/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
import java.lang.invoke.MemberName;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodTypeForm;
import java.lang.invoke.SimpleMethodHandle;
import java.util.Arrays;
import static java.lang.invoke.LambdaForm.*;
import static java.lang.invoke.MethodHandleStatics.*;

/**
 * A method handle whose invocation behavior is determined by a target.
 * The delegating MH itself can hold extra "intentions" beyond the simple behavior.
 * @author jrose
 */
/*non-public*/
abstract class DelegatingMethodHandle extends java.lang.invoke.MethodHandle {
    protected DelegatingMethodHandle(java.lang.invoke.MethodHandle target) {
        this(target.type(), target);
    }

    protected DelegatingMethodHandle(java.lang.invoke.MethodType type, java.lang.invoke.MethodHandle target) {
        super(type, chooseDelegatingForm(target));
    }

    protected DelegatingMethodHandle(java.lang.invoke.MethodType type, java.lang.invoke.LambdaForm form) {
        super(type, form);
    }

    /** Define this to extract the delegated target which supplies the invocation behavior. */
    abstract protected java.lang.invoke.MethodHandle getTarget();

    @Override
    abstract java.lang.invoke.MethodHandle asTypeUncached(java.lang.invoke.MethodType newType);

    @Override
    java.lang.invoke.MemberName internalMemberName() {
        return getTarget().internalMemberName();
    }

    @Override
    boolean isInvokeSpecial() {
        return getTarget().isInvokeSpecial();
    }

    @Override
    Class<?> internalCallerClass() {
        return getTarget().internalCallerClass();
    }

    @Override
    java.lang.invoke.MethodHandle copyWith(java.lang.invoke.MethodType mt, java.lang.invoke.LambdaForm lf) {
        // FIXME: rethink 'copyWith' protocol; it is too low-level for use on all MHs
        throw newIllegalArgumentException("do not use this");
    }

    @Override
    String internalProperties() {
        return "\n& Class="+getClass().getSimpleName()+
               "\n& Target="+getTarget().debugString();
    }

    @Override
    java.lang.invoke.BoundMethodHandle rebind() {
        return getTarget().rebind();
    }

    private static java.lang.invoke.LambdaForm chooseDelegatingForm(java.lang.invoke.MethodHandle target) {
        if (target instanceof java.lang.invoke.SimpleMethodHandle)
            return target.internalForm();  // no need for an indirection
        return makeReinvokerForm(target, java.lang.invoke.MethodTypeForm.LF_DELEGATE, DelegatingMethodHandle.class, NF_getTarget);
    }

    static java.lang.invoke.LambdaForm makeReinvokerForm(java.lang.invoke.MethodHandle target,
                                                         int whichCache,
                                                         Object constraint,
                                                         NamedFunction getTargetFn) {
        String debugString;
        switch(whichCache) {
            case java.lang.invoke.MethodTypeForm.LF_REBIND:            debugString = "BMH.reinvoke";      break;
            case java.lang.invoke.MethodTypeForm.LF_DELEGATE:          debugString = "MH.delegate";       break;
            default:                                  debugString = "MH.reinvoke";       break;
        }
        // No pre-action needed.
        return makeReinvokerForm(target, whichCache, constraint, debugString, true, getTargetFn, null);
    }
    /** Create a LF which simply reinvokes a target of the given basic type. */
    static java.lang.invoke.LambdaForm makeReinvokerForm(MethodHandle target,
                                                         int whichCache,
                                                         Object constraint,
                                                         String debugString,
                                                         boolean forceInline,
                                                         NamedFunction getTargetFn,
                                                         NamedFunction preActionFn) {
        java.lang.invoke.MethodType mtype = target.type().basicType();
        boolean customized = (whichCache < 0 ||
                mtype.parameterSlotCount() > MethodType.MAX_MH_INVOKER_ARITY);
        boolean hasPreAction = (preActionFn != null);
        java.lang.invoke.LambdaForm form;
        if (!customized) {
            form = mtype.form().cachedLambdaForm(whichCache);
            if (form != null)  return form;
        }
        final int THIS_DMH    = 0;
        final int ARG_BASE    = 1;
        final int ARG_LIMIT   = ARG_BASE + mtype.parameterCount();
        int nameCursor = ARG_LIMIT;
        final int PRE_ACTION   = hasPreAction ? nameCursor++ : -1;
        final int NEXT_MH     = customized ? -1 : nameCursor++;
        final int REINVOKE    = nameCursor++;
        java.lang.invoke.LambdaForm.Name[] names = java.lang.invoke.LambdaForm.arguments(nameCursor - ARG_LIMIT, mtype.invokerType());
        assert(names.length == nameCursor);
        names[THIS_DMH] = names[THIS_DMH].withConstraint(constraint);
        Object[] targetArgs;
        if (hasPreAction) {
            names[PRE_ACTION] = new java.lang.invoke.LambdaForm.Name(preActionFn, names[THIS_DMH]);
        }
        if (customized) {
            targetArgs = Arrays.copyOfRange(names, ARG_BASE, ARG_LIMIT, Object[].class);
            names[REINVOKE] = new java.lang.invoke.LambdaForm.Name(target, targetArgs);  // the invoker is the target itself
        } else {
            names[NEXT_MH] = new java.lang.invoke.LambdaForm.Name(getTargetFn, names[THIS_DMH]);
            targetArgs = Arrays.copyOfRange(names, THIS_DMH, ARG_LIMIT, Object[].class);
            targetArgs[0] = names[NEXT_MH];  // overwrite this MH with next MH
            names[REINVOKE] = new java.lang.invoke.LambdaForm.Name(mtype, targetArgs);
        }
        form = new java.lang.invoke.LambdaForm(debugString, ARG_LIMIT, names, forceInline);
        if (!customized) {
            form = mtype.form().setCachedLambdaForm(whichCache, form);
        }
        return form;
    }

    static final NamedFunction NF_getTarget;
    static {
        try {
            NF_getTarget = new NamedFunction(DelegatingMethodHandle.class
                                             .getDeclaredMethod("getTarget"));
        } catch (ReflectiveOperationException ex) {
            throw newInternalError(ex);
        }
    }
}
