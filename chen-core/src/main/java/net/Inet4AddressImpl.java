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
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetAddressImpl;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

/*
 * Package private implementation of InetAddressImpl for IPv4.
 *
 * @since 1.4
 */
class Inet4AddressImpl implements java.net.InetAddressImpl {
    public native String getLocalHostName() throws UnknownHostException;
    public native java.net.InetAddress[]
        lookupAllHostAddr(String hostname) throws UnknownHostException;
    public native String getHostByAddr(byte[] addr) throws UnknownHostException;
    private native boolean isReachable0(byte[] addr, int timeout, byte[] ifaddr, int ttl) throws IOException;

    public synchronized java.net.InetAddress anyLocalAddress() {
        if (anyLocalAddress == null) {
            anyLocalAddress = new java.net.Inet4Address(); // {0x00,0x00,0x00,0x00}
            anyLocalAddress.holder().hostName = "0.0.0.0";
        }
        return anyLocalAddress;
    }

    public synchronized java.net.InetAddress loopbackAddress() {
        if (loopbackAddress == null) {
            byte[] loopback = {0x7f,0x00,0x00,0x01};
            loopbackAddress = new java.net.Inet4Address("localhost", loopback);
        }
        return loopbackAddress;
    }

  public boolean isReachable(java.net.InetAddress addr, int timeout, NetworkInterface netif, int ttl) throws IOException {
      byte[] ifaddr = null;
      if (netif != null) {
          /*
           * Let's make sure we use an address of the proper family
           */
          java.util.Enumeration<java.net.InetAddress> it = netif.getInetAddresses();
          java.net.InetAddress inetaddr = null;
          while (!(inetaddr instanceof java.net.Inet4Address) &&
                 it.hasMoreElements())
              inetaddr = it.nextElement();
          if (inetaddr instanceof Inet4Address)
              ifaddr = inetaddr.getAddress();
      }
      return isReachable0(addr.getAddress(), timeout, ifaddr, ttl);
  }
    private java.net.InetAddress anyLocalAddress;
    private InetAddress loopbackAddress;
}
