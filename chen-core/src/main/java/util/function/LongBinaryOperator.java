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
import java.util.function.LongUnaryOperator;

/**
 * Represents an operation upon two {@code long}-valued operands and producing a
 * {@code long}-valued result.   This is the primitive type specialization of
 * {@link java.util.function.BinaryOperator} for {@code long}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong(long, long)}.
 *
 * @see BinaryOperator
 * @see LongUnaryOperator
 * @since 1.8
 */
@FunctionalInterface
public interface LongBinaryOperator {

    /**
     * Applies this operator to the given operands.
     *
     * @param left the first operand
     * @param right the second operand
     * @return the operator result
     */
    long applyAsLong(long left, long right);
}
