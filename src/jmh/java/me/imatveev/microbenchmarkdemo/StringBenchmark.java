package me.imatveev.microbenchmarkdemo;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 3, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
@Measurement(iterations = 3, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
public class StringBenchmark {
    private static final int COUNT = 10_000;

    @Benchmark
    public void stringForConcatenation1() {
        String str = "";
        for (int i = 0; i < COUNT; ++i) {
            str += i;
        }
        String result = str;
    }

    @Benchmark
    public void stringForConcatenation2() {
        String str = "";
        for (int i = 0; i < COUNT; i++) {
            str += i;
        }
        String result = str;
    }

    @Benchmark
    public void stringBuilderForConcatenation1() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < COUNT; i++) {
            str.append(i);
        }
        String result = str.toString();
    }

    @Benchmark
    public void stringBuilderForConcatenation2() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < COUNT; ++i) {
            str.append(i);
        }
        String result = str.toString();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .addProfiler(GCProfiler.class)
                .include(".*" + StringBenchmark.class.getSimpleName() + ".*")
                .build();
        new Runner(options).run();
    }
}
