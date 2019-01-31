package com.liuty.maven.jmhtest;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * 性能测试
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class JMHTest {

    static int millis = 24 * 3600 * 1000;
    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(JMHTest.class.getName()).forks(1).build();
        new Runner(options).run();
    }

    @Benchmark
    @Threads(5)
    public void runCalendar() {
        Calendar calendar = Calendar.getInstance();
    }

    @Benchmark
    @Threads(5)
    public void runJoda() {
        LocalDateTime ldt = LocalDateTime.now();
    }

    @Benchmark
    @Threads(5)
    public void runSystem() {
        long result = System.currentTimeMillis() / millis;
    }

}
