/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * An IllegalAccessException is thrown when an application tries
 * to reflectively create an instance (other than an array),
 * set or get a field, or invoke a method, but the currently
 * executing method does not have access to the definition of
 * the specified class, field, method or constructor.
 *
 * @author  unascribed
 * @see     java.lang.Class#newInstance()
 * @see     java.lang.reflect.Field#set(java.lang.Object, java.lang.Object)
 * @see     java.lang.reflect.Field#setBoolean(java.lang.Object, boolean)
 * @see     java.lang.reflect.Field#setByte(java.lang.Object, byte)
 * @see     java.lang.reflect.Field#setShort(java.lang.Object, short)
 * @see     java.lang.reflect.Field#setChar(java.lang.Object, char)
 * @see     java.lang.reflect.Field#setInt(java.lang.Object, int)
 * @see     java.lang.reflect.Field#setLong(java.lang.Object, long)
 * @see     java.lang.reflect.Field#setFloat(java.lang.Object, float)
 * @see     java.lang.reflect.Field#setDouble(java.lang.Object, double)
 * @see     java.lang.reflect.Field#get(java.lang.Object)
 * @see     java.lang.reflect.Field#getBoolean(java.lang.Object)
 * @see     java.lang.reflect.Field#getByte(java.lang.Object)
 * @see     java.lang.reflect.Field#getShort(java.lang.Object)
 * @see     java.lang.reflect.Field#getChar(java.lang.Object)
 * @see     java.lang.reflect.Field#getInt(java.lang.Object)
 * @see     java.lang.reflect.Field#getLong(java.lang.Object)
 * @see     java.lang.reflect.Field#getFloat(java.lang.Object)
 * @see     java.lang.reflect.Field#getDouble(java.lang.Object)
 * @see     java.lang.reflect.Method#invoke(java.lang.Object, java.lang.Object[])
 * @see     java.lang.reflect.Constructor#newInstance(java.lang.Object[])
 * @since   JDK1.0
 */
public class IllegalAccessException extends ReflectiveOperationException {
    private static final long serialVersionUID = 6616958222490762034L;

    /**
     * Constructs an <code>IllegalAccessException</code> without a
     * detail message.
     */
    public IllegalAccessException() {
        super();
    }

    /**
     * Constructs an <code>IllegalAccessException</code> with a detail message.
     *
     * @param   s   the detail message.
     */
    public IllegalAccessException(java.lang.String s) {
        super(s);
    }
}
