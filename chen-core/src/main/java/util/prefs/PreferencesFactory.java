/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.util.*;
import java.util.prefs.Preferences;

/**
 * A factory object that generates Preferences objects.  Providers of
 * new {@link java.util.prefs.Preferences} implementations should provide corresponding
 * <tt>PreferencesFactory</tt> implementations so that the new
 * <tt>Preferences</tt> implementation can be installed in place of the
 * platform-specific default implementation.
 *
 * <p><strong>This class is for <tt>Preferences</tt> implementers only.
 * Normal users of the <tt>Preferences</tt> facility should have no need to
 * consult this documentation.</strong>
 *
 * @author  Josh Bloch
 * @see     java.util.prefs.Preferences
 * @since   1.4
 */
public interface PreferencesFactory {
    /**
     * Returns the system root preference node.  (Multiple calls on this
     * method will return the same object reference.)
     * @return the system root preference node
     */
    java.util.prefs.Preferences systemRoot();

    /**
     * Returns the user root preference node corresponding to the calling
     * user.  In a server, the returned value will typically depend on
     * some implicit client-context.
     * @return the user root preference node corresponding to the calling
     * user
     */
    Preferences userRoot();
}
