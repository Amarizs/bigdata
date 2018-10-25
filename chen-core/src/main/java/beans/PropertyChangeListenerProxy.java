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

package beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EventListenerProxy;

/**
 * A class which extends the {@code EventListenerProxy}
 * specifically for adding a {@code PropertyChangeListener}
 * with a "bound" property.
 * Instances of this class can be added
 * as {@code PropertyChangeListener}s to a bean
 * which supports firing property change events.
 * <p>
 * If the object has a {@code getPropertyChangeListeners} method
 * then the array returned could be a mixture of {@code PropertyChangeListener}
 * and {@code PropertyChangeListenerProxy} objects.
 *
 * @see EventListenerProxy
 * @see PropertyChangeSupport#getPropertyChangeListeners
 * @since 1.4
 */
public class PropertyChangeListenerProxy
        extends EventListenerProxy<java.beans.PropertyChangeListener>
        implements java.beans.PropertyChangeListener {

    private final String propertyName;

    /**
     * Constructor which binds the {@code PropertyChangeListener}
     * to a specific property.
     *
     * @param propertyName  the name of the property to listen on
     * @param listener      the listener object
     */
    public PropertyChangeListenerProxy(String propertyName, PropertyChangeListener listener) {
        super(listener);
        this.propertyName = propertyName;
    }

    /**
     * Forwards the property change event to the listener delegate.
     *
     * @param event  the property change event
     */
    public void propertyChange(PropertyChangeEvent event) {
        getListener().propertyChange(event);
    }

    /**
     * Returns the name of the named property associated with the listener.
     *
     * @return the name of the named property associated with the listener
     */
    public String getPropertyName() {
        return this.propertyName;
    }
}
