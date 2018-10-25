/*
 * Copyright (c) 2002, 2005, Oracle and/or its affiliates. All rights reserved.
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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

/*
 * Package private interface to "implementation" used by
 * {@link InetAddress}.
 * <p>
 * See {@link java.net.Inet4AddressImp} and
 * {@link java.net.Inet6AddressImp}.
 *
 * @since 1.4
 */
interface InetAddressImpl {

    String getLocalHostName() throws UnknownHostException;
    java.net.InetAddress[]
        lookupAllHostAddr(String hostname) throws UnknownHostException;
    String getHostByAddr(byte[] addr) throws UnknownHostException;

    java.net.InetAddress anyLocalAddress();
    java.net.InetAddress loopbackAddress();
    boolean isReachable(InetAddress addr, int timeout, NetworkInterface netif,
                        int ttl) throws IOException;
}
