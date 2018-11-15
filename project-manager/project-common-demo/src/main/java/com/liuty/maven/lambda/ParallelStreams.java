package com.liuty.maven.lambda;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {

    /**
     * 顺序流计算1——n全部数值的和
     * @param n
     * @return
     */
    public static long sequentialSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
    }

    /**
     * 并行流计算1——n全部数值的和，利用多核
     * @param n
     * @return
     */
    public static long parallelSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }

    /**
     * 迭代器模式1——n全部数值的和
     * @param n
     * @return
     */
    public static long iterativeSum(long n){
        long result = 0L;
        for (long i = 1L; i < n; i++) {
            result += i;
        }
        return result;
    }
}
