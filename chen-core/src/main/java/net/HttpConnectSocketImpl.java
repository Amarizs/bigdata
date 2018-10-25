/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package net;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.AbstractPlainSocketImpl;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PlainSocketImpl;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Basic SocketImpl that relies on the internal HTTP protocol handler
 * implementation to perform the HTTP tunneling and authentication. The
 * sockets impl is swapped out and replaced with the socket from the HTTP
 * handler after the tunnel is successfully setup.
 *
 * @since 1.8
 */

/*package*/ class HttpConnectSocketImpl extends java.net.PlainSocketImpl {

    private static final String httpURLClazzStr =
                                  "sun.net.www.protocol.http.HttpURLConnection";
    private static final String netClientClazzStr = "sun.net.NetworkClient";
    private static final String doTunnelingStr = "doTunneling";
    private static final Field httpField;
    private static final Field serverSocketField;
    private static final Method doTunneling;

    private final String server;
    private java.net.InetSocketAddress external_address;
    private HashMap<Integer, Object> optionsMap = new HashMap<>();

    static  {
        try {
            Class<?> httpClazz = Class.forName(httpURLClazzStr, true, null);
            httpField = httpClazz.getDeclaredField("http");
            doTunneling = httpClazz.getDeclaredMethod(doTunnelingStr);
            Class<?> netClientClazz = Class.forName(netClientClazzStr, true, null);
            serverSocketField = netClientClazz.getDeclaredField("serverSocket");

            java.security.AccessController.doPrivileged(
                new java.security.PrivilegedAction<Void>() {
                    public Void run() {
                        httpField.setAccessible(true);
                        serverSocketField.setAccessible(true);
                        return null;
                }
            });
        } catch (ReflectiveOperationException x) {
            throw new InternalError("Should not reach here", x);
        }
    }

    HttpConnectSocketImpl(String server, int port) {
        this.server = server;
        this.port = port;
    }

    HttpConnectSocketImpl(java.net.Proxy proxy) {
        java.net.SocketAddress a = proxy.address();
        if ( !(a instanceof java.net.InetSocketAddress) )
            throw new IllegalArgumentException("Unsupported address type");

        java.net.InetSocketAddress ad = (java.net.InetSocketAddress) a;
        server = ad.getHostString();
        port = ad.getPort();
    }

    @Override
    protected void connect(SocketAddress endpoint, int timeout)
        throws IOException
    {
        if (endpoint == null || !(endpoint instanceof java.net.InetSocketAddress))
            throw new IllegalArgumentException("Unsupported address type");
        final java.net.InetSocketAddress epoint = (java.net.InetSocketAddress)endpoint;
        final String destHost = epoint.isUnresolved() ? epoint.getHostName()
                                                      : epoint.getAddress().getHostAddress();
        final int destPort = epoint.getPort();

        SecurityManager security = System.getSecurityManager();
        if (security != null)
            security.checkConnect(destHost, destPort);

        // Connect to the HTTP proxy server
        String urlString = "http://" + destHost + ":" + destPort;
        java.net.Socket httpSocket = privilegedDoTunnel(urlString, timeout);

        // Success!
        external_address = epoint;

        // close the original socket impl and release its descriptor
        close();

        // update the Sockets impl to the impl from the http Socket
        AbstractPlainSocketImpl psi = (AbstractPlainSocketImpl) httpSocket.impl;
        this.getSocket().impl = psi;

        // best effort is made to try and reset options previously set
        Set<Map.Entry<Integer,Object>> options = optionsMap.entrySet();
        try {
            for(Map.Entry<Integer,Object> entry : options) {
                psi.setOption(entry.getKey(), entry.getValue());
            }
        } catch (IOException x) {  /* gulp! */  }
    }

    @Override
    public void setOption(int opt, Object val) throws SocketException {
        super.setOption(opt, val);

        if (external_address != null)
            return;  // we're connected, just return

        // store options so that they can be re-applied to the impl after connect
        optionsMap.put(opt, val);
    }

    private java.net.Socket privilegedDoTunnel(final String urlString,
                                               final int timeout)
        throws IOException
    {
        try {
            return java.security.AccessController.doPrivileged(
                new java.security.PrivilegedExceptionAction<java.net.Socket>() {
                    public java.net.Socket run() throws IOException {
                        return doTunnel(urlString, timeout);
                }
            });
        } catch (java.security.PrivilegedActionException pae) {
            throw (IOException) pae.getException();
        }
    }

    private java.net.Socket doTunnel(String urlString, int connectTimeout)
        throws IOException
    {
        java.net.Proxy proxy = new java.net.Proxy(Proxy.Type.HTTP, new InetSocketAddress(server, port));
        java.net.URL destURL = new URL(urlString);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) destURL.openConnection(proxy);
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(this.timeout);
        conn.connect();
        doTunneling(conn);
        try {
            Object httpClient = httpField.get(conn);
            return (Socket) serverSocketField.get(httpClient);
        } catch (IllegalAccessException x) {
            throw new InternalError("Should not reach here", x);
        }
    }

    private void doTunneling(HttpURLConnection conn) {
        try {
            doTunneling.invoke(conn);
        } catch (ReflectiveOperationException x) {
            throw new InternalError("Should not reach here", x);
        }
    }

    @Override
    protected InetAddress getInetAddress() {
        if (external_address != null)
            return external_address.getAddress();
        else
            return super.getInetAddress();
    }

    @Override
    protected int getPort() {
        if (external_address != null)
            return external_address.getPort();
        else
            return super.getPort();
    }

    @Override
    protected int getLocalPort() {
        if (socket != null)
            return super.getLocalPort();
        if (external_address != null)
            return external_address.getPort();
        else
            return super.getLocalPort();
    }
}
