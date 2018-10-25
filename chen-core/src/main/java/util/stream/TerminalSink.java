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
package util.stream;

import java.util.function.Supplier;
import java.util.stream.Sink;

/**
 * A {@link java.util.stream.Sink} which accumulates state as elements are accepted, and allows
 * a result to be retrieved after the computation is finished.
 *
 * @param <T> the type of elements to be accepted
 * @param <R> the type of the result
 *
 * @since 1.8
 */
interface TerminalSink<T, R> extends java.util.stream.Sink<T>, Supplier<R> { }
