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

import java.util.function.Supplier;

/**
 * Represents a supplier of {@code long}-valued results.  This is the
 * {@code long}-producing primitive specialization of {@link java.util.function.Supplier}.
 *
 * <p>There is no requirement that a distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsLong()}.
 *
 * @see Supplier
 * @since 1.8
 */
@FunctionalInterface
public interface LongSupplier {

    /**
     * Gets a result.
     *
     * @return a result
     */
    long getAsLong();
}
