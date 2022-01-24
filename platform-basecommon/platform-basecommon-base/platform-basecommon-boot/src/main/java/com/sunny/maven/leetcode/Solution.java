package com.sunny.maven.leetcode;

/**
 * @author SUNNY
 * @description: 检测字母大写
 * @create: 2022-01-24 14:18
 */
public class Solution {
    public boolean detectCapitalUse(String word) {
        if (word.length() >= 2 && Character.isLowerCase(word.charAt(0)) && Character.isUpperCase(word.charAt(1))) {
            return false;
        }
        for (int i = 2, j = word.length(); i < j; i++) {
            if (Character.isLowerCase(word.charAt(i)) ^ Character.isLowerCase(word.charAt(1))) {
                return false;
            }
        }
        return true;
    }
}
