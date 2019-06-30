package com.liuty.maven.structure.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述: 字符计算
 * @创建人: Sunny
 * @创建时间: 2019/6/29
 */
public class StringComputeSolution {

    /**
     * 题目：
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * 示例 1:
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是子串的长度，"pwke" 是一个子序列，不是子串。
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        char[] cs = s.toCharArray();
        Map<Character, Integer> indexMap = new HashMap();
        int startIndex = 0, len = 0;
        for (int i = 0; i <cs.length; i++) {
            if (indexMap.containsKey(cs[i])) {
                int j = indexMap.get(cs[i]);
                if (j >= startIndex) {
                    startIndex = j + 1;
                }
            }
            int mid = i - startIndex + 1;
            len = Math.max(len, mid);
            indexMap.put(cs[i], i);
        }
        return len;
    }

    public static void main(String ...ars) {
        StringComputeSolution scs = new StringComputeSolution();
        String s = "abcabcbb";
        System.out.println(scs.lengthOfLongestSubstring(s));
        s = "bbbbb";
        System.out.println(scs.lengthOfLongestSubstring(s));
        s = "pwwkew";
        System.out.println(scs.lengthOfLongestSubstring(s));
        String s1 = "tmmzuxt";
        System.out.println(scs.lengthOfLongestSubstring(s1));
    }
}
