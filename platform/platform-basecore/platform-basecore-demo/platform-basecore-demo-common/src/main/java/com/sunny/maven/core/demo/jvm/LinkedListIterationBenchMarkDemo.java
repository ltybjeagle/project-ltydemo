package com.sunny.maven.core.demo.jvm;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: JMH性能测试
 * @create: 2023/7/4 15:22
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(Threads.MAX)
public class LinkedListIterationBenchMarkDemo {

    private static final int SIZE = 10000;

    private List<String> list = new LinkedList<>();

    @Setup
    public void setUp() {
        for (int i = 0; i < SIZE; i++) {
            list.add(String.valueOf(i));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void forIndexIterate() {
        for (int i = 0; i < SIZE; i++) {
            list.get(i);
            System.out.print("");
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void forEachIterate() {
        for (String s : list) {
            System.out.print("");
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().
                // 包含语义
                // 可以用方法名，也可以用XXX.class.getSimpleName()
                include(LinkedListIterationBenchMarkDemo.class.getSimpleName()).
                forks(0).
                // 预热2轮
                warmupIterations(2).
                // 代表正式计量测试做2轮，
                // 而每次都是先执行完预热再执行正式计量，
                // 内容都是调用标注了@Benchmark的代码。
                measurementIterations(2).
                build();
        new Runner(opt).run();
    }
}
