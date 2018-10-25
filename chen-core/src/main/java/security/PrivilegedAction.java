/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package security;


import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

/**
 * A computation to be performed with privileges enabled.  The computation is
 * performed by invoking {@code AccessController.doPrivileged} on the
 * {@code PrivilegedAction} object.  This interface is used only for
 * computations that do not throw checked exceptions; computations that
 * throw checked exceptions must use {@code PrivilegedExceptionAction}
 * instead.
 *
 * @see java.security.AccessController
 * @see java.security.AccessController#doPrivileged(PrivilegedAction)
 * @see PrivilegedExceptionAction
 */

public interface PrivilegedAction<T> {
    /**
     * Performs the computation.  This method will be called by
     * {@code AccessController.doPrivileged} after enabling privileges.
     *
     * @return a class-dependent value that may represent the results of the
     *         computation. Each class that implements
     *         {@code PrivilegedAction}
     *         should document what (if anything) this value represents.
     * @see java.security.AccessController#doPrivileged(PrivilegedAction)
     * @see AccessController#doPrivileged(PrivilegedAction,
     *                                     AccessControlContext)
     */
    T run();
}
