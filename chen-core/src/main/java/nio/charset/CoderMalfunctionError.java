/*
 * Copyright (c) 2001, 2007, Oracle and/or its affiliates. All rights reserved.
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


import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Error thrown when the {@link java.nio.charset.CharsetDecoder#decodeLoop decodeLoop} method of
 * a {@link CharsetDecoder}, or the {@link java.nio.charset.CharsetEncoder#encodeLoop
 * encodeLoop} method of a {@link CharsetEncoder}, throws an unexpected
 * exception.
 *
 * @since 1.4
 */

public class CoderMalfunctionError
    extends Error
{

    private static final long serialVersionUID = -1151412348057794301L;

    /**
     * Initializes an instance of this class.
     *
     * @param  cause
     *         The unexpected exception that was thrown
     */
    public CoderMalfunctionError(Exception cause) {
        super(cause);
    }

}
