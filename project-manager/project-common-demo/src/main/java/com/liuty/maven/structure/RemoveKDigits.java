package com.liuty.maven.structure;

/**
 *  贪心算法
 */
public class RemoveKDigits {

    public static String removeKDigits(String num, int k) {
        int newLen = num.length() - k;
        char[] newCh = new char[num.length()];
        int top = 0;
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            if (top > 0 && newCh[top - 1] > c && k > 0) {
                top -= 1;
                k -= 1;
            }
            newCh[top++] = c;
        }
        int firstK = 0;
        while (firstK < newLen && newCh[firstK] == '0') {
            firstK++;
        }
        return firstK == newLen ? "0" : new String(newCh, firstK, newCh.length - k);
    }

    public static void main(String ...args) {
        System.out.println(removeKDigits("1876239", 3));
    }
}
