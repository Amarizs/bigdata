/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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

package lang;

/**
 * Thrown when an application tries to access a type using a string
 * representing the type's name, but no definition for the type with
 * the specified name can be found.   This exception differs from
 * {@link java.lang.ClassNotFoundException} in that <tt>ClassNotFoundException</tt> is a
 * checked exception, whereas this exception is unchecked.
 *
 * <p>Note that this exception may be used when undefined type variables
 * are accessed as well as when types (e.g., classes, interfaces or
 * annotation types) are loaded.
 * In particular, this exception can be thrown by the {@linkplain
 * java.lang.reflect.AnnotatedElement API used to read annotations
 * reflectively}.
 *
 * @author  Josh Bloch
 * @see     java.lang.reflect.AnnotatedElement
 * @since 1.5
 */
public class TypeNotPresentException extends java.lang.RuntimeException {
    private static final long serialVersionUID = -5101214195716534496L;

    private java.lang.String typeName;

    /**
     * Constructs a <tt>TypeNotPresentException</tt> for the named type
     * with the specified cause.
     *
     * @param typeName the fully qualified name of the unavailable type
     * @param cause the exception that was thrown when the system attempted to
     *    load the named type, or <tt>null</tt> if unavailable or inapplicable
     */
    public TypeNotPresentException(java.lang.String typeName, java.lang.Throwable cause) {
        super("Type " + typeName + " not present", cause);
        this.typeName = typeName;
    }

    /**
     * Returns the fully qualified name of the unavailable type.
     *
     * @return the fully qualified name of the unavailable type
     */
    public java.lang.String typeName() { return typeName;}
}
