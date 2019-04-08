package com.liuty.maven.structure.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述:
 * @创建人: Sunny
 * @创建时间: 2019/3/26
 */
public class Solution {
    /**
     * 题目：
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     * 示例:
     *      给定 nums = [2, 7, 11, 15], target = 9
     *      因为 nums[0] + nums[1] = 2 + 7 = 9
     *      所以返回 [0, 1]
     * @param nums 数组
     * @param target 目标值
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] rst = {};
        if (nums.length < 2) {
            return rst;
        }
        /*
         * 方法一：基于集合实现，时间复杂度O(n)
         */
        Map<Integer, Integer> hashTable = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashTable.containsKey(target - nums[i])) {
                return new int[] {hashTable.get(target - nums[i]), i};
            }
            hashTable.put(nums[i], i);
        }

        /*
         * 方法二：暴力法，时间复杂度O(n^2)
         */
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }

        return rst;
    }

    public static void main(String ...args) {
        Solution solution = new Solution();
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println(solution.twoSum(nums, target));

    }
}
