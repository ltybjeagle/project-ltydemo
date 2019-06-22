package com.liuty.maven.structure.leetcode;

import lombok.val;

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
     * 给定一个整数数组nums和一个目标值 target，请你在该数组中找出和为目标值的那两个整数
     * ，并返回他们的数组下标。
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

    /**
     * 题目：
     * 给出两个非空的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的
     * ，并且它们的每个节点只能存储一位数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     *
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * 示例:
     *      输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     *      输出：7 -> 0 -> 8
     *      原因：342 + 465 = 807
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int digit = 0;
        int val1 = 0;
        ListNode midNode = l1;
        while (midNode != null) {
            val1 += midNode.val * 10 * digit;
            digit++;
            midNode = midNode.next;
        }
        digit = 0;
        int val2 = 0;
        midNode = l2;
        while (midNode != null) {
            val2 += midNode.val * 10 * digit;
            digit++;
            midNode = midNode.next;
        }
        int val = val1 + val2;
        ListNode firstNode = null, resNode;
        do {
            resNode = new ListNode(val % 10);
            val = val / 10;
            if (firstNode == null) {
                firstNode = resNode;
            }
        } while (val > 0);
        return firstNode;
    }

    public static void main(String ...args) {
        // 两数之和
        Solution solution = new Solution();
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println(solution.twoSum(nums, target));

        // 两数相加
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

    }

    public static void printListNode(ListNode ll) {
        boolean isBreak = ll != null;
        while (isBreak) {
            System.out.println(ll.val);
            System.out.println(" -> ");
            ll = ll.next;
            isBreak = ll != null;
            if (isBreak) {

            }
        }
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
