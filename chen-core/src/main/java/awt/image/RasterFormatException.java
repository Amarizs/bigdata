/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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

package awt.image;


import java.awt.image.Raster;

/**
 * The <code>RasterFormatException</code> is thrown if there is
 * invalid layout information in the {@link Raster}.
 */
public class RasterFormatException extends RuntimeException {

    /**
     * Constructs a new <code>RasterFormatException</code> with the
     * specified message.
     * @param s the message to generate when a
     * <code>RasterFormatException</code> is thrown
     */
    public RasterFormatException(String s) {
        super (s);
    }
}
