/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.io.NotSerializableException;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.Preferences;

/**
 * An event emitted by a <tt>Preferences</tt> node to indicate that
 * a child of that node has been added or removed.<p>
 *
 * Note, that although NodeChangeEvent inherits Serializable interface from
 * java.util.EventObject, it is not intended to be Serializable. Appropriate
 * serialization methods are implemented to throw NotSerializableException.
 *
 * @author  Josh Bloch
 * @see     java.util.prefs.Preferences
 * @see     NodeChangeListener
 * @see     PreferenceChangeEvent
 * @since   1.4
 * @serial  exclude
 */

public class NodeChangeEvent extends java.util.EventObject {
    /**
     * The node that was added or removed.
     *
     * @serial
     */
    private java.util.prefs.Preferences child;

    /**
     * Constructs a new <code>NodeChangeEvent</code> instance.
     *
     * @param parent  The parent of the node that was added or removed.
     * @param child   The node that was added or removed.
     */
    public NodeChangeEvent(java.util.prefs.Preferences parent, java.util.prefs.Preferences child) {
        super(parent);
        this.child = child;
    }

    /**
     * Returns the parent of the node that was added or removed.
     *
     * @return  The parent Preferences node whose child was added or removed
     */
    public java.util.prefs.Preferences getParent() {
        return (java.util.prefs.Preferences) getSource();
    }

    /**
     * Returns the node that was added or removed.
     *
     * @return  The node that was added or removed.
     */
    public Preferences getChild() {
        return child;
    }

    /**
     * Throws NotSerializableException, since NodeChangeEvent objects are not
     * intended to be serializable.
     */
     private void writeObject(java.io.ObjectOutputStream out)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    /**
     * Throws NotSerializableException, since NodeChangeEvent objects are not
     * intended to be serializable.
     */
     private void readObject(java.io.ObjectInputStream in)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    // Defined so that this class isn't flagged as a potential problem when
    // searches for missing serialVersionUID fields are done.
    private static final long serialVersionUID = 8068949086596572957L;
}
