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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;
import java.util.stream.*;
import java.util.stream.ForEachOps;
import java.util.stream.Node;
import java.util.stream.Nodes;
import java.util.stream.ReduceOps;
import java.util.stream.Sink;
import java.util.stream.TerminalOp;

/**
 * Factory methods for transforming streams into duplicate-free streams, using
 * {@link Object#equals(Object)} to determine equality.
 *
 * @since 1.8
 */
final class DistinctOps {

    private DistinctOps() { }

    /**
     * Appends a "distinct" operation to the provided stream, and returns the
     * new stream.
     *
     * @param <T> the type of both input and output elements
     * @param upstream a reference stream with element type T
     * @return the new stream
     */
    static <T> ReferencePipeline<T, T> makeRef(AbstractPipeline<?, T, ?> upstream) {
        return new ReferencePipeline.StatefulOp<T, T>(upstream, StreamShape.REFERENCE,
                                                      StreamOpFlag.IS_DISTINCT | StreamOpFlag.NOT_SIZED) {

            <P_IN> java.util.stream.Node<T> reduce(PipelineHelper<T> helper, Spliterator<P_IN> spliterator) {
                // If the stream is SORTED then it should also be ORDERED so the following will also
                // preserve the sort order
                java.util.stream.TerminalOp<T, LinkedHashSet<T>> reduceOp
                        = java.util.stream.ReduceOps.<T, LinkedHashSet<T>>makeRef(LinkedHashSet::new, LinkedHashSet::add,
                                                                 LinkedHashSet::addAll);
                return java.util.stream.Nodes.node(reduceOp.evaluateParallel(helper, spliterator));
            }

            @Override
            <P_IN> java.util.stream.Node<T> opEvaluateParallel(PipelineHelper<T> helper,
                                                               Spliterator<P_IN> spliterator,
                                                               IntFunction<T[]> generator) {
                if (StreamOpFlag.DISTINCT.isKnown(helper.getStreamAndOpFlags())) {
                    // No-op
                    return helper.evaluate(spliterator, false, generator);
                }
                else if (StreamOpFlag.ORDERED.isKnown(helper.getStreamAndOpFlags())) {
                    return reduce(helper, spliterator);
                }
                else {
                    // Holder of null state since ConcurrentHashMap does not support null values
                    AtomicBoolean seenNull = new AtomicBoolean(false);
                    ConcurrentHashMap<T, Boolean> map = new ConcurrentHashMap<>();
                    java.util.stream.TerminalOp<T, Void> forEachOp = java.util.stream.ForEachOps.makeRef(t -> {
                        if (t == null)
                            seenNull.set(true);
                        else
                            map.putIfAbsent(t, Boolean.TRUE);
                    }, false);
                    forEachOp.evaluateParallel(helper, spliterator);

                    // If null has been seen then copy the key set into a HashSet that supports null values
                    // and add null
                    Set<T> keys = map.keySet();
                    if (seenNull.get()) {
                        // TODO Implement a more efficient set-union view, rather than copying
                        keys = new HashSet<>(keys);
                        keys.add(null);
                    }
                    return java.util.stream.Nodes.node(keys);
                }
            }

            @Override
            <P_IN> Spliterator<T> opEvaluateParallelLazy(PipelineHelper<T> helper, Spliterator<P_IN> spliterator) {
                if (StreamOpFlag.DISTINCT.isKnown(helper.getStreamAndOpFlags())) {
                    // No-op
                    return helper.wrapSpliterator(spliterator);
                }
                else if (StreamOpFlag.ORDERED.isKnown(helper.getStreamAndOpFlags())) {
                    // Not lazy, barrier required to preserve order
                    return reduce(helper, spliterator).spliterator();
                }
                else {
                    // Lazy
                    return new StreamSpliterators.DistinctSpliterator<>(helper.wrapSpliterator(spliterator));
                }
            }

            @Override
            java.util.stream.Sink<T> opWrapSink(int flags, java.util.stream.Sink<T> sink) {
                Objects.requireNonNull(sink);

                if (StreamOpFlag.DISTINCT.isKnown(flags)) {
                    return sink;
                } else if (StreamOpFlag.SORTED.isKnown(flags)) {
                    return new java.util.stream.Sink.ChainedReference<T, T>(sink) {
                        boolean seenNull;
                        T lastSeen;

                        @Override
                        public void begin(long size) {
                            seenNull = false;
                            lastSeen = null;
                            downstream.begin(-1);
                        }

                        @Override
                        public void end() {
                            seenNull = false;
                            lastSeen = null;
                            downstream.end();
                        }

                        @Override
                        public void accept(T t) {
                            if (t == null) {
                                if (!seenNull) {
                                    seenNull = true;
                                    downstream.accept(lastSeen = null);
                                }
                            } else if (lastSeen == null || !t.equals(lastSeen)) {
                                downstream.accept(lastSeen = t);
                            }
                        }
                    };
                } else {
                    return new java.util.stream.Sink.ChainedReference<T, T>(sink) {
                        Set<T> seen;

                        @Override
                        public void begin(long size) {
                            seen = new HashSet<>();
                            downstream.begin(-1);
                        }

                        @Override
                        public void end() {
                            seen = null;
                            downstream.end();
                        }

                        @Override
                        public void accept(T t) {
                            if (!seen.contains(t)) {
                                seen.add(t);
                                downstream.accept(t);
                            }
                        }
                    };
                }
            }
        };
    }
}
