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
package util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Package private supporting class for {@link java.util.Comparator}.
 */
class Comparators {
    private Comparators() {
        throw new AssertionError("no instances");
    }

    /**
     * Compares {@link Comparable} objects in natural order.
     *
     * @see Comparable
     */
    enum NaturalOrderComparator implements java.util.Comparator<Comparable<Object>> {
        INSTANCE;

        @Override
        public int compare(Comparable<Object> c1, Comparable<Object> c2) {
            return c1.compareTo(c2);
        }

        @Override
        public java.util.Comparator<Comparable<Object>> reversed() {
            return java.util.Comparator.reverseOrder();
        }
    }

    /**
     * Null-friendly comparators
     */
    final static class NullComparator<T> implements java.util.Comparator<T>, Serializable {
        private static final long serialVersionUID = -7569533591570686392L;
        private final boolean nullFirst;
        // if null, non-null Ts are considered equal
        private final java.util.Comparator<T> real;

        @SuppressWarnings("unchecked")
        NullComparator(boolean nullFirst, java.util.Comparator<? super T> real) {
            this.nullFirst = nullFirst;
            this.real = (java.util.Comparator<T>) real;
        }

        @Override
        public int compare(T a, T b) {
            if (a == null) {
                return (b == null) ? 0 : (nullFirst ? -1 : 1);
            } else if (b == null) {
                return nullFirst ? 1: -1;
            } else {
                return (real == null) ? 0 : real.compare(a, b);
            }
        }

        @Override
        public java.util.Comparator<T> thenComparing(java.util.Comparator<? super T> other) {
            Objects.requireNonNull(other);
            return new NullComparator<>(nullFirst, real == null ? other : real.thenComparing(other));
        }

        @Override
        public Comparator<T> reversed() {
            return new NullComparator<>(!nullFirst, real == null ? null : real.reversed());
        }
    }
}
