/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package util.prefs;

import java.util.prefs.NodeChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

/**
 * A listener for receiving preference node change events.
 *
 * @author  Josh Bloch
 * @see     Preferences
 * @see     java.util.prefs.NodeChangeEvent
 * @see     PreferenceChangeListener
 * @since   1.4
 */

public interface NodeChangeListener extends java.util.EventListener {
    /**
     * This method gets called when a child node is added.
     *
     * @param evt A node change event object describing the parent
     *            and child node.
     */
    void childAdded(java.util.prefs.NodeChangeEvent evt);

    /**
     * This method gets called when a child node is removed.
     *
     * @param evt A node change event object describing the parent
     *            and child node.
     */
    void childRemoved(NodeChangeEvent evt);
}
