/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
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

import java.net.ProtocolFamily;

/**
 * Defines the standard families of communication protocols.
 *
 * @since 1.7
 */

public enum StandardProtocolFamily implements ProtocolFamily {

    /**
     * Internet Protocol Version 4 (IPv4)
     */
    INET,

    /**
     * Internet Protocol Version 6 (IPv6)
     */
    INET6
}
