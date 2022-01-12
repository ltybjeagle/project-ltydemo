package com.sunny.maven.leetcode;

/**
 * @author SUNNY
 * @description: 304. ⼆维区域和检索 - 矩阵不可变
 * @create: 2021-12-19 22:05
 */
public class NumMatrix {

    /**
     * preSum[i][j] 记录矩阵 [0, 0, i, j] 的元素和
     */
    private int[][] preSum;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        if (n == 0) {
            return;
        }
        // 构造前缀和矩阵
        preSum = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 计算每个矩阵 [0, 0, i, j] 的元素和
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] + matrix[i - 1][j -1] - preSum[i - 1][j -1];
            }
        }
    }

    /**
     * 计算⼦矩阵 [x1, y1, x2, y2] 的元素和
     */
    public int sumRegion(int x1, int y1, int x2, int y2) {
        return preSum[x2 + 1][y2 + 1] - preSum[x1][y2 + 1] - preSum[x2 + 1][y1] + preSum[x1][y1];
    }

    public static void main(String[] args) {
        NumMatrix numMatrix = new NumMatrix(new int[][]{{3, 0, 1, 4}, {5, 6, 3, 2}, {1, 2, 0, 1}, {4, 1, 0, 1}});
        System.out.println(numMatrix.sumRegion(1, 1, 3, 3));
    }
}
