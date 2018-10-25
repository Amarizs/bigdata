/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
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
package nio.charset;

import java.nio.charset.Charset;

/**
 * Constant definitions for the standard {@link java.nio.charset.Charset Charsets}. These
 * charsets are guaranteed to be available on every implementation of the Java
 * platform.
 *
 * @see <a href="Charset#standard">Standard Charsets</a>
 * @since 1.7
 */
public final class StandardCharsets {

    private StandardCharsets() {
        throw new AssertionError("No java.nio.charset.StandardCharsets instances for you!");
    }
    /**
     * Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the
     * Unicode character set
     */
    public static final java.nio.charset.Charset US_ASCII = java.nio.charset.Charset.forName("US-ASCII");
    /**
     * ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
     */
    public static final java.nio.charset.Charset ISO_8859_1 = java.nio.charset.Charset.forName("ISO-8859-1");
    /**
     * Eight-bit UCS Transformation Format
     */
    public static final java.nio.charset.Charset UTF_8 = java.nio.charset.Charset.forName("UTF-8");
    /**
     * Sixteen-bit UCS Transformation Format, big-endian byte order
     */
    public static final java.nio.charset.Charset UTF_16BE = java.nio.charset.Charset.forName("UTF-16BE");
    /**
     * Sixteen-bit UCS Transformation Format, little-endian byte order
     */
    public static final java.nio.charset.Charset UTF_16LE = java.nio.charset.Charset.forName("UTF-16LE");
    /**
     * Sixteen-bit UCS Transformation Format, byte order identified by an
     * optional byte-order mark
     */
    public static final java.nio.charset.Charset UTF_16 = Charset.forName("UTF-16");
}
