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
import java.security.PrivilegedAction;

/**
 * A computation to be performed with privileges enabled, that throws one or
 * more checked exceptions.  The computation is performed by invoking
 * {@code AccessController.doPrivileged} on the
 * {@code PrivilegedExceptionAction} object.  This interface is
 * used only for computations that throw checked exceptions;
 * computations that do not throw
 * checked exceptions should use {@code PrivilegedAction} instead.
 *
 * @see java.security.AccessController
 * @see java.security.AccessController#doPrivileged(PrivilegedExceptionAction)
 * @see java.security.AccessController#doPrivileged(PrivilegedExceptionAction,
 *                                              java.security.AccessControlContext)
 * @see PrivilegedAction
 */

public interface PrivilegedExceptionAction<T> {
    /**
     * Performs the computation.  This method will be called by
     * {@code AccessController.doPrivileged} after enabling privileges.
     *
     * @return a class-dependent value that may represent the results of the
     *         computation.  Each class that implements
     *         {@code PrivilegedExceptionAction} should document what
     *         (if anything) this value represents.
     * @throws Exception an exceptional condition has occurred.  Each class
     *         that implements {@code PrivilegedExceptionAction} should
     *         document the exceptions that its run method can throw.
     * @see java.security.AccessController#doPrivileged(PrivilegedExceptionAction)
     * @see AccessController#doPrivileged(PrivilegedExceptionAction, AccessControlContext)
     */

    T run() throws Exception;
}
