package com.liuty.maven.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: 计数排序
 * @author: Sunny
 * @date: 2019/9/9
 */
public class CountingSort {

    /**
     * 计数排序：用于有范围的整数数组排序
     * 时间复杂度：O(m + n)
     * 空间复杂度：O(m)
     * 局限性：1、整数跨度很大的时候不适用，浪费时间、空间。2、浮点数不适用，无法对应计数数组的下标
     */
    public static int[] countSort(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
            if (min > array[i]) {
                min = array[i];
            }
        }
        int countNum = max - min;
        int[] countArray = new int[countNum + 1];
        for (int i = 0; i < array.length; i++) {
            countArray[array[i] - min]++;
        }
        int sum = 0;
        for (int i = 0; i < countArray.length; i++) {
            sum += countArray[i];
            countArray[i] = sum;
        }
        int[] sortArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            sortArray[countArray[array[i] - min] - 1] = array[i];
            countArray[array[i] - min]--;
        }
        return sortArray;
    }

    /**
     * 桶排序：基于计数排序实现
     * 时间复杂度：O(m + n)
     * 空间复杂度：O(m)
     * 桶区间跨度 = (最大值 - 最小值) / (桶数量 - 1)
     * @param array
     * @return
     */
    public static double[] bucketSort(double[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        // 获取数组最大值、最小值
        double max = array[0];
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        double d = max - min;
        // 初始化桶
        int bucketSize = array.length;
        List<LinkedList<Double>> bucketList = new ArrayList<>(bucketSize);
        for (int i = 0; i < bucketSize; i++) {
            bucketList.add(new LinkedList<Double>());
        }
        // 遍历数组，将数据放入桶中
        for (int i = 0; i < array.length; i++) {
            int index = (int) ((array[i] - min) * (bucketSize - 1) / d);
            bucketList.get(index).add(array[i]);
        }
        // 桶内数据排序
        for (LinkedList<Double> linkedList : bucketList) {
            // 使用JDK类库，底层采用归并排序
            Collections.sort(linkedList);
        }
        // 输出排序结果数组
        double[] sortArray = new double[array.length];
        int index = 0;
        for (LinkedList<Double> linkedList : bucketList) {
            for (Double doubleVal : linkedList) {
                sortArray[index] = doubleVal;
                index++;
            }
        }
        return sortArray;
    }
}
