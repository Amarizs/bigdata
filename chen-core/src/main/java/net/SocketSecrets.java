/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.SocketOption;

class SocketSecrets {

    /* accessed by reflection from jdk.net.Sockets */

    /* obj must be a Socket or ServerSocket */

    private static <T> void setOption(Object obj, java.net.SocketOption<T> name, T value) throws IOException {
        java.net.SocketImpl impl;

        if (obj instanceof java.net.Socket) {
            impl = ((java.net.Socket)obj).getImpl();
        } else if (obj instanceof java.net.ServerSocket) {
            impl = ((java.net.ServerSocket)obj).getImpl();
        } else {
            throw new IllegalArgumentException();
        }
        impl.setOption(name, value);
    }

    private static <T> T getOption(Object obj, java.net.SocketOption<T> name) throws IOException {
        SocketImpl impl;

        if (obj instanceof java.net.Socket) {
            impl = ((Socket)obj).getImpl();
        } else if (obj instanceof java.net.ServerSocket) {
            impl = ((ServerSocket)obj).getImpl();
        } else {
            throw new IllegalArgumentException();
        }
        return impl.getOption(name);
    }

    private static <T> void setOption(DatagramSocket s, java.net.SocketOption<T> name, T value) throws IOException {
        s.getImpl().setOption(name, value);
    }

    private static <T> T getOption(DatagramSocket s, SocketOption<T> name) throws IOException {
        return s.getImpl().getOption(name);
    }

}
