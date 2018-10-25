/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
package util.function;

import java.util.function.BinaryOperator;
import java.util.function.IntUnaryOperator;

/**
 * Represents an operation upon two {@code int}-valued operands and producing an
 * {@code int}-valued result.   This is the primitive type specialization of
 * {@link java.util.function.BinaryOperator} for {@code int}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsInt(int, int)}.
 *
 * @see BinaryOperator
 * @see IntUnaryOperator
 * @since 1.8
 */
@FunctionalInterface
public interface IntBinaryOperator {

    /**
     * Applies this operator to the given operands.
     *
     * @param left the first operand
     * @param right the second operand
     * @return the operator result
     */
    int applyAsInt(int left, int right);
}
