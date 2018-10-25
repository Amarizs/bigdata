/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
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
import java.io.FileDescriptor;
import java.net.AbstractPlainDatagramSocketImpl;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import sun.net.ResourceManager;

/**
 * This class defines the plain DatagramSocketImpl that is used for all
 * Windows versions lower than Vista. It adds support for IPv6 on
 * these platforms where available.
 *
 * For backward compatibility windows platforms that do not have IPv6
 * support also use this implementation, and fd1 gets set to null
 * during socket creation.
 *
 * @author Chris Hegarty
 */

class TwoStacksPlainDatagramSocketImpl extends java.net.AbstractPlainDatagramSocketImpl
{
    /* Used for IPv6 on Windows only */
    private FileDescriptor fd1;

    /*
     * Needed for ipv6 on windows because we need to know
     * if the socket was bound to ::0 or 0.0.0.0, when a caller
     * asks for it. In this case, both sockets are used, but we
     * don't know whether the caller requested ::0 or 0.0.0.0
     * and need to remember it here.
     */
    private java.net.InetAddress anyLocalBoundAddr=null;

    private int fduse=-1; /* saved between peek() and receive() calls */

    /* saved between successive calls to receive, if data is detected
     * on both sockets at same time. To ensure that one socket is not
     * starved, they rotate using this field
     */
    private int lastfd=-1;

    static {
        init();
    }

    // true if this socket is exclusively bound
    private final boolean exclusiveBind;

    /*
     * Set to true if SO_REUSEADDR is set after the socket is bound to
     * indicate SO_REUSEADDR is being emulated
     */
    private boolean reuseAddressEmulated;

    // emulates SO_REUSEADDR when exclusiveBind is true and socket is bound
    private boolean isReuseAddress;

    TwoStacksPlainDatagramSocketImpl(boolean exclBind) {
        exclusiveBind = exclBind;
    }

    protected synchronized void create() throws java.net.SocketException {
        fd1 = new FileDescriptor();
        try {
            super.create();
        } catch (java.net.SocketException e) {
            fd1 = null;
            throw e;
        }
    }

    protected synchronized void bind(int lport, java.net.InetAddress laddr)
        throws java.net.SocketException {
        super.bind(lport, laddr);
        if (laddr.isAnyLocalAddress()) {
            anyLocalBoundAddr = laddr;
        }
    }

    @Override
    protected synchronized void bind0(int lport, java.net.InetAddress laddr)
        throws java.net.SocketException
    {
        bind0(lport, laddr, exclusiveBind);

    }

    protected synchronized void receive(java.net.DatagramPacket p)
        throws IOException {
        try {
            receive0(p);
        } finally {
            fduse = -1;
        }
    }

    public Object getOption(int optID) throws java.net.SocketException {
        if (isClosed()) {
            throw new java.net.SocketException("Socket Closed");
        }

        if (optID == SO_BINDADDR) {
            if ((fd != null && fd1 != null) && !connected) {
                return anyLocalBoundAddr;
            }
            int family = connectedAddress == null ? -1 : connectedAddress.holder().getFamily();
            return socketLocalAddress(family);
        } else if (optID == SO_REUSEADDR && reuseAddressEmulated) {
            return isReuseAddress;
        } else {
            return super.getOption(optID);
        }
    }

    protected void socketSetOption(int opt, Object val)
        throws java.net.SocketException
    {
        if (opt == SO_REUSEADDR && exclusiveBind && localPort != 0)  {
            // socket already bound, emulate
            reuseAddressEmulated = true;
            isReuseAddress = (Boolean)val;
        } else {
            socketNativeSetOption(opt, val);
        }

    }

    protected boolean isClosed() {
        return (fd == null && fd1 == null) ? true : false;
    }

    protected void close() {
        if (fd != null || fd1 != null) {
            datagramSocketClose();
            ResourceManager.afterUdpClose();
            fd = null;
            fd1 = null;
        }
    }

    /* Native methods */

    protected synchronized native void bind0(int lport, java.net.InetAddress laddr,
                                             boolean exclBind)
        throws java.net.SocketException;

    protected native void send(java.net.DatagramPacket p) throws IOException;

    protected synchronized native int peek(java.net.InetAddress i) throws IOException;

    protected synchronized native int peekData(java.net.DatagramPacket p) throws IOException;

    protected synchronized native void receive0(DatagramPacket p)
        throws IOException;

    protected native void setTimeToLive(int ttl) throws IOException;

    protected native int getTimeToLive() throws IOException;

    @Deprecated
    protected native void setTTL(byte ttl) throws IOException;

    @Deprecated
    protected native byte getTTL() throws IOException;

    protected native void join(java.net.InetAddress inetaddr, java.net.NetworkInterface netIf)
        throws IOException;

    protected native void leave(java.net.InetAddress inetaddr, NetworkInterface netIf)
        throws IOException;

    protected native void datagramSocketCreate() throws java.net.SocketException;

    protected native void datagramSocketClose();

    protected native void socketNativeSetOption(int opt, Object val)
        throws java.net.SocketException;

    protected native Object socketGetOption(int opt) throws java.net.SocketException;

    protected native void connect0(InetAddress address, int port) throws java.net.SocketException;

    protected native Object socketLocalAddress(int family) throws SocketException;

    protected native void disconnect0(int family);

    native int dataAvailable();

    /**
     * Perform class load-time initializations.
     */
    private native static void init();
}
