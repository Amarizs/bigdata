/*
 * Copyright (c) 2003, 2006, Oracle and/or its affiliates. All rights reserved.
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
package awt.datatransfer;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorListener;
import java.util.EventObject;


/**
 * <code>FlavorEvent</code> is used to notify interested parties
 * that available {@link DataFlavor}s have changed in the
 * {@link java.awt.datatransfer.Clipboard} (the event source).
 *
 * @see FlavorListener
 *
 * @author Alexander Gerasimov
 * @since 1.5
 */
public class FlavorEvent extends EventObject {
    /**
     * Constructs a <code>FlavorEvent</code> object.
     *
     * @param source  the <code>Clipboard</code> that is the source of the event
     *
     * @throws IllegalArgumentException if the {@code source} is {@code null}
     */
    public FlavorEvent(Clipboard source) {
        super(source);
    }
}
