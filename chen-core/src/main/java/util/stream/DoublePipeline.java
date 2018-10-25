/*
 * Copyright (c) 2013, 2014, Oracle and/or its affiliates. All rights reserved.
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

import java.util.DoubleSummaryStatistics;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.FindOps;
import java.util.stream.ForEachOps;
import java.util.stream.IntPipeline;
import java.util.stream.IntStream;
import java.util.stream.LongPipeline;
import java.util.stream.LongStream;
import java.util.stream.MatchOps;
import java.util.stream.Node;
import java.util.stream.Nodes;
import java.util.stream.ReduceOps;
import java.util.stream.Sink;
import java.util.stream.SliceOps;
import java.util.stream.SortedOps;
import java.util.stream.Stream;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream.Tripwire;

/**
 * Abstract base class for an intermediate pipeline stage or pipeline source
 * stage implementing whose elements are of type {@code double}.
 *
 * @param <E_IN> type of elements in the upstream source
 *
 * @since 1.8
 */
abstract class DoublePipeline<E_IN>
        extends AbstractPipeline<E_IN, Double, java.util.stream.DoubleStream>
        implements java.util.stream.DoubleStream {

    /**
     * Constructor for the head of a stream pipeline.
     *
     * @param source {@code Supplier<Spliterator>} describing the stream source
     * @param sourceFlags the source flags for the stream source, described in
     * {@link java.util.stream.StreamOpFlag}
     */
    DoublePipeline(Supplier<? extends Spliterator<Double>> source,
                   int sourceFlags, boolean parallel) {
        super(source, sourceFlags, parallel);
    }

    /**
     * Constructor for the head of a stream pipeline.
     *
     * @param source {@code Spliterator} describing the stream source
     * @param sourceFlags the source flags for the stream source, described in
     * {@link java.util.stream.StreamOpFlag}
     */
    DoublePipeline(Spliterator<Double> source,
                   int sourceFlags, boolean parallel) {
        super(source, sourceFlags, parallel);
    }

    /**
     * Constructor for appending an intermediate operation onto an existing
     * pipeline.
     *
     * @param upstream the upstream element source.
     * @param opFlags the operation flags
     */
    DoublePipeline(AbstractPipeline<?, E_IN, ?> upstream, int opFlags) {
        super(upstream, opFlags);
    }

    /**
     * Adapt a {@code Sink<Double> to a {@code DoubleConsumer}, ideally simply
     * by casting.
     */
    private static DoubleConsumer adapt(java.util.stream.Sink<Double> sink) {
        if (sink instanceof DoubleConsumer) {
            return (DoubleConsumer) sink;
        } else {
            if (java.util.stream.Tripwire.ENABLED)
                java.util.stream.Tripwire.trip(AbstractPipeline.class,
                              "using DoubleStream.adapt(Sink<Double> s)");
            return sink::accept;
        }
    }

    /**
     * Adapt a {@code Spliterator<Double>} to a {@code Spliterator.OfDouble}.
     *
     * @implNote
     * The implementation attempts to cast to a Spliterator.OfDouble, and throws
     * an exception if this cast is not possible.
     */
    private static Spliterator.OfDouble adapt(Spliterator<Double> s) {
        if (s instanceof Spliterator.OfDouble) {
            return (Spliterator.OfDouble) s;
        } else {
            if (java.util.stream.Tripwire.ENABLED)
                java.util.stream.Tripwire.trip(AbstractPipeline.class,
                              "using DoubleStream.adapt(Spliterator<Double> s)");
            throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
        }
    }


    // Shape-specific methods

    @Override
    final java.util.stream.StreamShape getOutputShape() {
        return java.util.stream.StreamShape.DOUBLE_VALUE;
    }

    @Override
    final <P_IN> java.util.stream.Node<Double> evaluateToNode(PipelineHelper<Double> helper,
                                                              Spliterator<P_IN> spliterator,
                                                              boolean flattenTree,
                                                              IntFunction<Double[]> generator) {
        return java.util.stream.Nodes.collectDouble(helper, spliterator, flattenTree);
    }

    @Override
    final <P_IN> Spliterator<Double> wrap(PipelineHelper<Double> ph,
                                          Supplier<Spliterator<P_IN>> supplier,
                                          boolean isParallel) {
        return new StreamSpliterators.DoubleWrappingSpliterator<>(ph, supplier, isParallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    final Spliterator.OfDouble lazySpliterator(Supplier<? extends Spliterator<Double>> supplier) {
        return new StreamSpliterators.DelegatingSpliterator.OfDouble((Supplier<Spliterator.OfDouble>) supplier);
    }

    @Override
    final void forEachWithCancel(Spliterator<Double> spliterator, java.util.stream.Sink<Double> sink) {
        Spliterator.OfDouble spl = adapt(spliterator);
        DoubleConsumer adaptedSink = adapt(sink);
        do { } while (!sink.cancellationRequested() && spl.tryAdvance(adaptedSink));
    }

    @Override
    final  java.util.stream.Node.Builder<Double> makeNodeBuilder(long exactSizeIfKnown, IntFunction<Double[]> generator) {
        return java.util.stream.Nodes.doubleBuilder(exactSizeIfKnown);
    }


    // DoubleStream

    @Override
    public final PrimitiveIterator.OfDouble iterator() {
        return Spliterators.iterator(spliterator());
    }

    @Override
    public final Spliterator.OfDouble spliterator() {
        return adapt(super.spliterator());
    }

    // Stateless intermediate ops from DoubleStream

    @Override
    public final java.util.stream.Stream<Double> boxed() {
        return mapToObj(Double::valueOf);
    }

    @Override
    public final java.util.stream.DoubleStream map(DoubleUnaryOperator mapper) {
        Objects.requireNonNull(mapper);
        return new StatelessOp<Double>(this, java.util.stream.StreamShape.DOUBLE_VALUE,
                                       java.util.stream.StreamOpFlag.NOT_SORTED | java.util.stream.StreamOpFlag.NOT_DISTINCT) {
            @Override
            java.util.stream.Sink<Double> opWrapSink(int flags, java.util.stream.Sink<Double> sink) {
                return new java.util.stream.Sink.ChainedDouble<Double>(sink) {
                    @Override
                    public void accept(double t) {
                        downstream.accept(mapper.applyAsDouble(t));
                    }
                };
            }
        };
    }

    @Override
    public final <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        Objects.requireNonNull(mapper);
        return new ReferencePipeline.StatelessOp<Double, U>(this, java.util.stream.StreamShape.DOUBLE_VALUE,
                                                            java.util.stream.StreamOpFlag.NOT_SORTED | java.util.stream.StreamOpFlag.NOT_DISTINCT) {
            @Override
            java.util.stream.Sink<Double> opWrapSink(int flags, java.util.stream.Sink<U> sink) {
                return new java.util.stream.Sink.ChainedDouble<U>(sink) {
                    @Override
                    public void accept(double t) {
                        downstream.accept(mapper.apply(t));
                    }
                };
            }
        };
    }

    @Override
    public final IntStream mapToInt(DoubleToIntFunction mapper) {
        Objects.requireNonNull(mapper);
        return new java.util.stream.IntPipeline.StatelessOp<Double>(this, java.util.stream.StreamShape.DOUBLE_VALUE,
                                                   java.util.stream.StreamOpFlag.NOT_SORTED | java.util.stream.StreamOpFlag.NOT_DISTINCT) {
            @Override
            java.util.stream.Sink<Double> opWrapSink(int flags, java.util.stream.Sink<Integer> sink) {
                return new java.util.stream.Sink.ChainedDouble<Integer>(sink) {
                    @Override
                    public void accept(double t) {
                        downstream.accept(mapper.applyAsInt(t));
                    }
                };
            }
        };
    }

    @Override
    public final LongStream mapToLong(DoubleToLongFunction mapper) {
        Objects.requireNonNull(mapper);
        return new java.util.stream.LongPipeline.StatelessOp<Double>(this, java.util.stream.StreamShape.DOUBLE_VALUE,
                                                    java.util.stream.StreamOpFlag.NOT_SORTED | java.util.stream.StreamOpFlag.NOT_DISTINCT) {
            @Override
            java.util.stream.Sink<Double> opWrapSink(int flags, java.util.stream.Sink<Long> sink) {
                return new java.util.stream.Sink.ChainedDouble<Long>(sink) {
                    @Override
                    public void accept(double t) {
                        downstream.accept(mapper.applyAsLong(t));
                    }
                };
            }
        };
    }

    @Override
    public final java.util.stream.DoubleStream flatMap(DoubleFunction<? extends java.util.stream.DoubleStream> mapper) {
        return new StatelessOp<Double>(this, java.util.stream.StreamShape.DOUBLE_VALUE,
                                        java.util.stream.StreamOpFlag.NOT_SORTED | java.util.stream.StreamOpFlag.NOT_DISTINCT | java.util.stream.StreamOpFlag.NOT_SIZED) {
            @Override
            java.util.stream.Sink<Double> opWrapSink(int flags, java.util.stream.Sink<Double> sink) {
                return new java.util.stream.Sink.ChainedDouble<Double>(sink) {
                    @Override
                    public void begin(long size) {
                        downstream.begin(-1);
                    }

                    @Override
                    public void accept(double t) {
                        try (java.util.stream.DoubleStream result = mapper.apply(t)) {
                            // We can do better that this too; optimize for depth=0 case and just grab spliterator and forEach it
                            if (result != null)
                                result.sequential().forEach(i -> downstream.accept(i));
                        }
                    }
                };
            }
        };
    }

    @Override
    public java.util.stream.DoubleStream unordered() {
        if (!isOrdered())
            return this;
        return new StatelessOp<Double>(this, java.util.stream.StreamShape.DOUBLE_VALUE, java.util.stream.StreamOpFlag.NOT_ORDERED) {
            @Override
            java.util.stream.Sink<Double> opWrapSink(int flags, java.util.stream.Sink<Double> sink) {
                return sink;
            }
        };
    }

    @Override
    public final java.util.stream.DoubleStream filter(DoublePredicate predicate) {
        Objects.requireNonNull(predicate);
        return new StatelessOp<Double>(this, java.util.stream.StreamShape.DOUBLE_VALUE,
                                       java.util.stream.StreamOpFlag.NOT_SIZED) {
            @Override
            java.util.stream.Sink<Double> opWrapSink(int flags, java.util.stream.Sink<Double> sink) {
                return new java.util.stream.Sink.ChainedDouble<Double>(sink) {
                    @Override
                    public void begin(long size) {
                        downstream.begin(-1);
                    }

                    @Override
                    public void accept(double t) {
                        if (predicate.test(t))
                            downstream.accept(t);
                    }
                };
            }
        };
    }

    @Override
    public final java.util.stream.DoubleStream peek(DoubleConsumer action) {
        Objects.requireNonNull(action);
        return new StatelessOp<Double>(this, java.util.stream.StreamShape.DOUBLE_VALUE,
                                       0) {
            @Override
            java.util.stream.Sink<Double> opWrapSink(int flags, java.util.stream.Sink<Double> sink) {
                return new java.util.stream.Sink.ChainedDouble<Double>(sink) {
                    @Override
                    public void accept(double t) {
                        action.accept(t);
                        downstream.accept(t);
                    }
                };
            }
        };
    }

    // Stateful intermediate ops from DoubleStream

    @Override
    public final java.util.stream.DoubleStream limit(long maxSize) {
        if (maxSize < 0)
            throw new IllegalArgumentException(Long.toString(maxSize));
        return java.util.stream.SliceOps.makeDouble(this, (long) 0, maxSize);
    }

    @Override
    public final java.util.stream.DoubleStream skip(long n) {
        if (n < 0)
            throw new IllegalArgumentException(Long.toString(n));
        if (n == 0)
            return this;
        else {
            long limit = -1;
            return java.util.stream.SliceOps.makeDouble(this, n, limit);
        }
    }

    @Override
    public final java.util.stream.DoubleStream sorted() {
        return java.util.stream.SortedOps.makeDouble(this);
    }

    @Override
    public final DoubleStream distinct() {
        // While functional and quick to implement, this approach is not very efficient.
        // An efficient version requires a double-specific map/set implementation.
        return boxed().distinct().mapToDouble(i -> (double) i);
    }

    // Terminal ops from DoubleStream

    @Override
    public void forEach(DoubleConsumer consumer) {
        evaluate(java.util.stream.ForEachOps.makeDouble(consumer, false));
    }

    @Override
    public void forEachOrdered(DoubleConsumer consumer) {
        evaluate(java.util.stream.ForEachOps.makeDouble(consumer, true));
    }

    @Override
    public final double sum() {
        /*
         * In the arrays allocated for the collect operation, index 0
         * holds the high-order bits of the running sum, index 1 holds
         * the low-order bits of the sum computed via compensated
         * summation, and index 2 holds the simple sum used to compute
         * the proper result if the stream contains infinite values of
         * the same sign.
         */
        double[] summation = collect(() -> new double[3],
                               (ll, d) -> {
                                   java.util.stream.Collectors.sumWithCompensation(ll, d);
                                   ll[2] += d;
                               },
                               (ll, rr) -> {
                                   java.util.stream.Collectors.sumWithCompensation(ll, rr[0]);
                                   java.util.stream.Collectors.sumWithCompensation(ll, rr[1]);
                                   ll[2] += rr[2];
                               });

        return java.util.stream.Collectors.computeFinalSum(summation);
    }

    @Override
    public final OptionalDouble min() {
        return reduce(Math::min);
    }

    @Override
    public final OptionalDouble max() {
        return reduce(Math::max);
    }

    /**
     * {@inheritDoc}
     *
     * @implNote The {@code double} format can represent all
     * consecutive integers in the range -2<sup>53</sup> to
     * 2<sup>53</sup>. If the pipeline has more than 2<sup>53</sup>
     * values, the divisor in the average computation will saturate at
     * 2<sup>53</sup>, leading to additional numerical errors.
     */
    @Override
    public final OptionalDouble average() {
        /*
         * In the arrays allocated for the collect operation, index 0
         * holds the high-order bits of the running sum, index 1 holds
         * the low-order bits of the sum computed via compensated
         * summation, index 2 holds the number of values seen, index 3
         * holds the simple sum.
         */
        double[] avg = collect(() -> new double[4],
                               (ll, d) -> {
                                   ll[2]++;
                                   java.util.stream.Collectors.sumWithCompensation(ll, d);
                                   ll[3] += d;
                               },
                               (ll, rr) -> {
                                   java.util.stream.Collectors.sumWithCompensation(ll, rr[0]);
                                   java.util.stream.Collectors.sumWithCompensation(ll, rr[1]);
                                   ll[2] += rr[2];
                                   ll[3] += rr[3];
                               });
        return avg[2] > 0
            ? OptionalDouble.of(Collectors.computeFinalSum(avg) / avg[2])
            : OptionalDouble.empty();
    }

    @Override
    public final long count() {
        return mapToLong(e -> 1L).sum();
    }

    @Override
    public final DoubleSummaryStatistics summaryStatistics() {
        return collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept,
                       DoubleSummaryStatistics::combine);
    }

    @Override
    public final double reduce(double identity, DoubleBinaryOperator op) {
        return evaluate(java.util.stream.ReduceOps.makeDouble(identity, op));
    }

    @Override
    public final OptionalDouble reduce(DoubleBinaryOperator op) {
        return evaluate(java.util.stream.ReduceOps.makeDouble(op));
    }

    @Override
    public final <R> R collect(Supplier<R> supplier,
                               ObjDoubleConsumer<R> accumulator,
                               BiConsumer<R, R> combiner) {
        BinaryOperator<R> operator = (left, right) -> {
            combiner.accept(left, right);
            return left;
        };
        return evaluate(java.util.stream.ReduceOps.makeDouble(supplier, accumulator, operator));
    }

    @Override
    public final boolean anyMatch(DoublePredicate predicate) {
        return evaluate(java.util.stream.MatchOps.makeDouble(predicate, java.util.stream.MatchOps.MatchKind.ANY));
    }

    @Override
    public final boolean allMatch(DoublePredicate predicate) {
        return evaluate(java.util.stream.MatchOps.makeDouble(predicate, java.util.stream.MatchOps.MatchKind.ALL));
    }

    @Override
    public final boolean noneMatch(DoublePredicate predicate) {
        return evaluate(java.util.stream.MatchOps.makeDouble(predicate, java.util.stream.MatchOps.MatchKind.NONE));
    }

    @Override
    public final OptionalDouble findFirst() {
        return evaluate(java.util.stream.FindOps.makeDouble(true));
    }

    @Override
    public final OptionalDouble findAny() {
        return evaluate(java.util.stream.FindOps.makeDouble(false));
    }

    @Override
    public final double[] toArray() {
        return java.util.stream.Nodes.flattenDouble((java.util.stream.Node.OfDouble) evaluateToArrayNode(Double[]::new))
                        .asPrimitiveArray();
    }

    //

    /**
     * Source stage of a DoubleStream
     *
     * @param <E_IN> type of elements in the upstream source
     */
    static class Head<E_IN> extends DoublePipeline<E_IN> {
        /**
         * Constructor for the source stage of a DoubleStream.
         *
         * @param source {@code Supplier<Spliterator>} describing the stream
         *               source
         * @param sourceFlags the source flags for the stream source, described
         *                    in {@link java.util.stream.StreamOpFlag}
         * @param parallel {@code true} if the pipeline is parallel
         */
        Head(Supplier<? extends Spliterator<Double>> source,
             int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        /**
         * Constructor for the source stage of a DoubleStream.
         *
         * @param source {@code Spliterator} describing the stream source
         * @param sourceFlags the source flags for the stream source, described
         *                    in {@link java.util.stream.StreamOpFlag}
         * @param parallel {@code true} if the pipeline is parallel
         */
        Head(Spliterator<Double> source,
             int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        @Override
        final boolean opIsStateful() {
            throw new UnsupportedOperationException();
        }

        @Override
        final java.util.stream.Sink<E_IN> opWrapSink(int flags, java.util.stream.Sink<Double> sink) {
            throw new UnsupportedOperationException();
        }

        // Optimized sequential terminal operations for the head of the pipeline

        @Override
        public void forEach(DoubleConsumer consumer) {
            if (!isParallel()) {
                adapt(sourceStageSpliterator()).forEachRemaining(consumer);
            }
            else {
                super.forEach(consumer);
            }
        }

        @Override
        public void forEachOrdered(DoubleConsumer consumer) {
            if (!isParallel()) {
                adapt(sourceStageSpliterator()).forEachRemaining(consumer);
            }
            else {
                super.forEachOrdered(consumer);
            }
        }

    }

    /**
     * Base class for a stateless intermediate stage of a DoubleStream.
     *
     * @param <E_IN> type of elements in the upstream source
     * @since 1.8
     */
    abstract static class StatelessOp<E_IN> extends DoublePipeline<E_IN> {
        /**
         * Construct a new DoubleStream by appending a stateless intermediate
         * operation to an existing stream.
         *
         * @param upstream the upstream pipeline stage
         * @param inputShape the stream shape for the upstream pipeline stage
         * @param opFlags operation flags for the new stage
         */
        StatelessOp(AbstractPipeline<?, E_IN, ?> upstream,
                    java.util.stream.StreamShape inputShape,
                    int opFlags) {
            super(upstream, opFlags);
            assert upstream.getOutputShape() == inputShape;
        }

        @Override
        final boolean opIsStateful() {
            return false;
        }
    }

    /**
     * Base class for a stateful intermediate stage of a DoubleStream.
     *
     * @param <E_IN> type of elements in the upstream source
     * @since 1.8
     */
    abstract static class StatefulOp<E_IN> extends DoublePipeline<E_IN> {
        /**
         * Construct a new DoubleStream by appending a stateful intermediate
         * operation to an existing stream.
         *
         * @param upstream the upstream pipeline stage
         * @param inputShape the stream shape for the upstream pipeline stage
         * @param opFlags operation flags for the new stage
         */
        StatefulOp(AbstractPipeline<?, E_IN, ?> upstream,
                   java.util.stream.StreamShape inputShape,
                   int opFlags) {
            super(upstream, opFlags);
            assert upstream.getOutputShape() == inputShape;
        }

        @Override
        final boolean opIsStateful() {
            return true;
        }

        @Override
        abstract <P_IN> java.util.stream.Node<Double> opEvaluateParallel(PipelineHelper<Double> helper,
                                                                         Spliterator<P_IN> spliterator,
                                                                         IntFunction<Double[]> generator);
    }
}
