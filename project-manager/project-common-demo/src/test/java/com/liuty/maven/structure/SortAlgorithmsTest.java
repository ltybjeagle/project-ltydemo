package com.liuty.maven.structure;

import org.junit.Test;

import java.util.Arrays;

public class SortAlgorithmsTest {

    /**
     * 计数排序测试
     */
    @Test
    public void countSortTest() {
        int[] array = new int[] {95, 94, 91, 98, 99, 90, 99, 93, 91, 92};
        int[] result = SortAlgorithms.countSort(array);
        System.out.println(Arrays.toString(result));
    }

    /**
     * 桶排序测试
     */
    @Test
    public void bucketSortTest() {
        double[] array = new double[] {4.12, 6.421, 0.0023, 3.0, 2.123, 8.122, 4.12, 10.09};
        double[] result = SortAlgorithms.bucketSort(array);
        System.out.println(Arrays.toString(result));
    }
}
