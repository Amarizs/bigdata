/*
 * Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown when an application tries to access an enum constant by name
 * and the enum type contains no constant with the specified name.
 * This exception can be thrown by the {@linkplain
 * java.lang.reflect.AnnotatedElement API used to read annotations
 * reflectively}.
 *
 * @author  Josh Bloch
 * @see     java.lang.reflect.AnnotatedElement
 * @since   1.5
 */
@java.lang.SuppressWarnings("rawtypes") /* rawtypes are part of the public api */
public class EnumConstantNotPresentException extends java.lang.RuntimeException {
    private static final long serialVersionUID = -6046998521960521108L;

    /**
     * The type of the missing enum constant.
     */
    private java.lang.Class<? extends java.lang.Enum> enumType;

    /**
     * The name of the missing enum constant.
     */
    private java.lang.String constantName;

    /**
     * Constructs an <tt>EnumConstantNotPresentException</tt> for the
     * specified constant.
     *
     * @param enumType the type of the missing enum constant
     * @param constantName the name of the missing enum constant
     */
    public EnumConstantNotPresentException(java.lang.Class<? extends java.lang.Enum> enumType,
                                           java.lang.String constantName) {
        super(enumType.getName() + "." + constantName);
        this.enumType = enumType;
        this.constantName  = constantName;
    }

    /**
     * Returns the type of the missing enum constant.
     *
     * @return the type of the missing enum constant
     */
    public java.lang.Class<? extends java.lang.Enum> enumType() { return enumType; }

    /**
     * Returns the name of the missing enum constant.
     *
     * @return the name of the missing enum constant
     */
    public java.lang.String constantName() { return constantName; }
}
