/*
 * Copyright (c) 2003, 2014, Oracle and/or its affiliates. All rights reserved.
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


package awt;

import java.awt.*;
import java.awt.MouseInfo;
import java.awt.Point;

/**
 * A class that describes the pointer position.
 * It provides the {@code GraphicsDevice} where the pointer is and
 * the {@code Point} that represents the coordinates of the pointer.
 * <p>
 * Instances of this class should be obtained via
 * {@link java.awt.MouseInfo#getPointerInfo}.
 * The {@code PointerInfo} instance is not updated dynamically as the mouse
 * moves. To get the updated location, you must call
 * {@link java.awt.MouseInfo#getPointerInfo} again.
 *
 * @see java.awt.MouseInfo#getPointerInfo
 * @author Roman Poborchiy
 * @since 1.5
 */
public class PointerInfo {

    private final GraphicsDevice device;
    private final java.awt.Point location;

    /**
     * Package-private constructor to prevent instantiation.
     */
    PointerInfo(final GraphicsDevice device, final java.awt.Point location) {
        this.device = device;
        this.location = location;
    }

    /**
     * Returns the {@code GraphicsDevice} where the mouse pointer was at the
     * moment this {@code PointerInfo} was created.
     *
     * @return {@code GraphicsDevice} corresponding to the pointer
     * @since 1.5
     */
    public GraphicsDevice getDevice() {
        return device;
    }

    /**
     * Returns the {@code Point} that represents the coordinates of the pointer
     * on the screen. See {@link java.awt.MouseInfo#getPointerInfo} for more information
     * about coordinate calculation for multiscreen systems.
     *
     * @return coordinates of mouse pointer
     * @see java.awt.MouseInfo
     * @see MouseInfo#getPointerInfo
     * @since 1.5
     */
    public Point getLocation() {
        return location;
    }
}
