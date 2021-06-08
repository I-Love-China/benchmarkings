package io.github.bhuwanupadhyay.tutorial;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 原文地址: https://www.cnblogs.com/fightfordream/p/9353002.html
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class ListTraverseBenchmarking {
    @Param({"100", "1000"})
    private int iterateTimes;

    private List<Integer> arrayList;
    private List<Integer> linkedList;

    @Setup(Level.Trial)
    public void init() {
        arrayList = new ArrayList<>(iterateTimes);
        linkedList = new LinkedList<>();

        for (int i = 0; i < iterateTimes; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
    }

    @Benchmark
    public void arrayTraverse() {
        for (int i = 0; i < iterateTimes; i++) {
            arrayList.get(i);
        }
    }

    @Benchmark
    public void linkedListTraverse() {
        for (int i = 0; i < iterateTimes; i++) {
            linkedList.get(i);
        }
    }

    @TearDown
    public void arrayClear() {
        arrayList.clear();
        linkedList.clear();
    }

    @Test
    public void runBenchmarks() throws Exception {
        Options opts = new OptionsBuilder()
                // set the class name regex for benchmarks to search for to the current class
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(1)
                .measurementIterations(1)
                // do not use forking or the benchmark methods will not see references stored within its class
                .forks(0)
                // do not use multiple threads
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(opts).run();
    }

}
