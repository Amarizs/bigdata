/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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

package lang.reflect;

import sun.reflect.MethodAccessor;
import sun.reflect.ConstructorAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/** Package-private class implementing the
    sun.reflect.LangReflectAccess interface, allowing the java.lang
    package to instantiate objects in this package. */

class ReflectAccess implements sun.reflect.LangReflectAccess {
    public java.lang.reflect.Field newField(Class<?> declaringClass,
                                            String name,
                                            Class<?> type,
                                            int modifiers,
                                            int slot,
                                            String signature,
                                            byte[] annotations)
    {
        return new java.lang.reflect.Field(declaringClass,
                         name,
                         type,
                         modifiers,
                         slot,
                         signature,
                         annotations);
    }

    public java.lang.reflect.Method newMethod(Class<?> declaringClass,
                                              String name,
                                              Class<?>[] parameterTypes,
                                              Class<?> returnType,
                                              Class<?>[] checkedExceptions,
                                              int modifiers,
                                              int slot,
                                              String signature,
                                              byte[] annotations,
                                              byte[] parameterAnnotations,
                                              byte[] annotationDefault)
    {
        return new java.lang.reflect.Method(declaringClass,
                          name,
                          parameterTypes,
                          returnType,
                          checkedExceptions,
                          modifiers,
                          slot,
                          signature,
                          annotations,
                          parameterAnnotations,
                          annotationDefault);
    }

    public <T> java.lang.reflect.Constructor<T> newConstructor(Class<T> declaringClass,
                                                               Class<?>[] parameterTypes,
                                                               Class<?>[] checkedExceptions,
                                                               int modifiers,
                                                               int slot,
                                                               String signature,
                                                               byte[] annotations,
                                                               byte[] parameterAnnotations)
    {
        return new java.lang.reflect.Constructor<>(declaringClass,
                                  parameterTypes,
                                  checkedExceptions,
                                  modifiers,
                                  slot,
                                  signature,
                                  annotations,
                                  parameterAnnotations);
    }

    public MethodAccessor getMethodAccessor(java.lang.reflect.Method m) {
        return m.getMethodAccessor();
    }

    public void setMethodAccessor(java.lang.reflect.Method m, MethodAccessor accessor) {
        m.setMethodAccessor(accessor);
    }

    public ConstructorAccessor getConstructorAccessor(java.lang.reflect.Constructor<?> c) {
        return c.getConstructorAccessor();
    }

    public void setConstructorAccessor(java.lang.reflect.Constructor<?> c,
                                       ConstructorAccessor accessor)
    {
        c.setConstructorAccessor(accessor);
    }

    public int getConstructorSlot(java.lang.reflect.Constructor<?> c) {
        return c.getSlot();
    }

    public String getConstructorSignature(java.lang.reflect.Constructor<?> c) {
        return c.getSignature();
    }

    public byte[] getConstructorAnnotations(java.lang.reflect.Constructor<?> c) {
        return c.getRawAnnotations();
    }

    public byte[] getConstructorParameterAnnotations(java.lang.reflect.Constructor<?> c) {
        return c.getRawParameterAnnotations();
    }

    public byte[] getExecutableTypeAnnotationBytes(Executable ex) {
        return ex.getTypeAnnotationBytes();
    }

    //
    // Copying routines, needed to quickly fabricate new Field,
    // Method, and Constructor objects from templates
    //
    public java.lang.reflect.Method copyMethod(Method arg) {
        return arg.copy();
    }

    public java.lang.reflect.Field copyField(Field arg) {
        return arg.copy();
    }

    public <T> java.lang.reflect.Constructor<T> copyConstructor(Constructor<T> arg) {
        return arg.copy();
    }
}
