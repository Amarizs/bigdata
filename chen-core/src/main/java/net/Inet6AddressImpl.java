/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.net.Inet4AddressImpl;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetAddressImpl;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

/*
 * Package private implementation of InetAddressImpl for dual
 * IPv4/IPv6 stack.
 * <p>
 * If InetAddress.preferIPv6Address is true then anyLocalAddress(),
 * loopbackAddress(), and localHost() will return IPv6 addresses,
 * otherwise IPv4 addresses.
 *
 * @since 1.4
 */

class Inet6AddressImpl implements java.net.InetAddressImpl {
    public native String getLocalHostName() throws UnknownHostException;
    public native java.net.InetAddress[]
        lookupAllHostAddr(String hostname) throws UnknownHostException;
    public native String getHostByAddr(byte[] addr) throws UnknownHostException;
    private native boolean isReachable0(byte[] addr, int scope, int timeout, byte[] inf, int ttl, int if_scope) throws IOException;

    public boolean isReachable(java.net.InetAddress addr, int timeout, NetworkInterface netif, int ttl) throws IOException {
        byte[] ifaddr = null;
        int scope = -1;
        int netif_scope = -1;
        if (netif != null) {
            /*
             * Let's make sure we bind to an address of the proper family.
             * Which means same family as addr because at this point it could
             * be either an IPv6 address or an IPv4 address (case of a dual
             * stack system).
             */
            java.util.Enumeration<java.net.InetAddress> it = netif.getInetAddresses();
            java.net.InetAddress inetaddr = null;
            while (it.hasMoreElements()) {
                inetaddr = it.nextElement();
                if (inetaddr.getClass().isInstance(addr)) {
                    ifaddr = inetaddr.getAddress();
                    if (inetaddr instanceof java.net.Inet6Address) {
                        netif_scope = ((java.net.Inet6Address) inetaddr).getScopeId();
                    }
                    break;
                }
            }
            if (ifaddr == null) {
                // Interface doesn't support the address family of
                // the destination
                return false;
            }
        }
        if (addr instanceof java.net.Inet6Address)
            scope = ((java.net.Inet6Address) addr).getScopeId();
        return isReachable0(addr.getAddress(), scope, timeout, ifaddr, ttl, netif_scope);
    }

    public synchronized java.net.InetAddress anyLocalAddress() {
        if (anyLocalAddress == null) {
            if (java.net.InetAddress.preferIPv6Address) {
                anyLocalAddress = new java.net.Inet6Address();
                anyLocalAddress.holder().hostName = "::";
            } else {
                anyLocalAddress = (new java.net.Inet4AddressImpl()).anyLocalAddress();
            }
        }
        return anyLocalAddress;
    }

    public synchronized java.net.InetAddress loopbackAddress() {
        if (loopbackAddress == null) {
             if (java.net.InetAddress.preferIPv6Address) {
                 byte[] loopback =
                        {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                         0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01};
                 loopbackAddress = new Inet6Address("localhost", loopback);
             } else {
                loopbackAddress = (new java.net.Inet4AddressImpl()).loopbackAddress();
             }
        }
        return loopbackAddress;
    }

    private java.net.InetAddress anyLocalAddress;
    private InetAddress loopbackAddress;
}
