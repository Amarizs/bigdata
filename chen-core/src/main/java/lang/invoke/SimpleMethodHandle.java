/*
 * Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved.
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

package lang.invoke;

import java.lang.invoke.BoundMethodHandle;
import java.lang.invoke.LambdaForm;
import java.lang.invoke.MethodType;

import static java.lang.invoke.LambdaForm.BasicType.*;
import static java.lang.invoke.MethodHandleStatics.*;

/**
 * A method handle whose behavior is determined only by its LambdaForm.
 * @author jrose
 */
final class SimpleMethodHandle extends java.lang.invoke.BoundMethodHandle {
    private SimpleMethodHandle(java.lang.invoke.MethodType type, java.lang.invoke.LambdaForm form) {
        super(type, form);
    }

    /*non-public*/ static java.lang.invoke.BoundMethodHandle make(java.lang.invoke.MethodType type, java.lang.invoke.LambdaForm form) {
        return new SimpleMethodHandle(type, form);
    }

    /*non-public*/ static final SpeciesData SPECIES_DATA = SpeciesData.EMPTY;

    /*non-public*/ public SpeciesData speciesData() {
            return SPECIES_DATA;
    }

    @Override
    /*non-public*/ java.lang.invoke.BoundMethodHandle copyWith(java.lang.invoke.MethodType mt, java.lang.invoke.LambdaForm lf) {
        return make(mt, lf);
    }

    @Override
    String internalProperties() {
        return "\n& Class="+getClass().getSimpleName();
    }

    @Override
    /*non-public*/ public int fieldCount() {
        return 0;
    }

    @Override
    /*non-public*/ final java.lang.invoke.BoundMethodHandle copyWithExtendL(java.lang.invoke.MethodType mt, java.lang.invoke.LambdaForm lf, Object narg) {
        return java.lang.invoke.BoundMethodHandle.bindSingle(mt, lf, narg); // Use known fast path.
    }
    @Override
    /*non-public*/ final java.lang.invoke.BoundMethodHandle copyWithExtendI(java.lang.invoke.MethodType mt, java.lang.invoke.LambdaForm lf, int narg) {
        try {
            return (java.lang.invoke.BoundMethodHandle) SPECIES_DATA.extendWith(I_TYPE).constructor().invokeBasic(mt, lf, narg);
        } catch (Throwable ex) {
            throw uncaughtException(ex);
        }
    }
    @Override
    /*non-public*/ final java.lang.invoke.BoundMethodHandle copyWithExtendJ(java.lang.invoke.MethodType mt, java.lang.invoke.LambdaForm lf, long narg) {
        try {
            return (java.lang.invoke.BoundMethodHandle) SPECIES_DATA.extendWith(J_TYPE).constructor().invokeBasic(mt, lf, narg);
        } catch (Throwable ex) {
            throw uncaughtException(ex);
        }
    }
    @Override
    /*non-public*/ final java.lang.invoke.BoundMethodHandle copyWithExtendF(java.lang.invoke.MethodType mt, java.lang.invoke.LambdaForm lf, float narg) {
        try {
            return (java.lang.invoke.BoundMethodHandle) SPECIES_DATA.extendWith(F_TYPE).constructor().invokeBasic(mt, lf, narg);
        } catch (Throwable ex) {
            throw uncaughtException(ex);
        }
    }
    @Override
    /*non-public*/ final java.lang.invoke.BoundMethodHandle copyWithExtendD(MethodType mt, java.lang.invoke.LambdaForm lf, double narg) {
        try {
            return (java.lang.invoke.BoundMethodHandle) SPECIES_DATA.extendWith(D_TYPE).constructor().invokeBasic(mt, lf, narg);
        } catch (Throwable ex) {
            throw uncaughtException(ex);
        }
    }
}
