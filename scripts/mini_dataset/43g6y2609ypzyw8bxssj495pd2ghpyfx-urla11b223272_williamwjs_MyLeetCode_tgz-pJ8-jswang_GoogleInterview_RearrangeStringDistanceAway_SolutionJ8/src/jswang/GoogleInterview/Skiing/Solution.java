package jswang.GoogleInterview.Skiing;

/**
 * Created by willwjs on 10/10/15.
 */
public class Solution {
    public int findLongestContinuous(int[][] array) {
        if (array == null || array.length == 0 || array[0].length == 0) return 0;
        int[][] dp = new int[array.length][array[0].length];
        int max = 0;
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[0].length; ++j) {
                if (dp[i][j] == 0) {
                    dfs(array, dp, i, j);
                }
                if (dp[i][j] > max) max = dp[i][j];
            }
        }
        return max;
    }

    private void dfs(int[][] array, int[][] dp, int i, int j) {
        dp[i][j] = 1;
        if (i > 0 && array[i - 1][j] == array[i][j] + 1) {
            if (dp[i - 1][j] == 0) {
                dfs(array, dp, i - 1, j);
            }
            dp[i][j] = Math.max(dp[i - 1][j] + 1, dp[i][j]);
        }
        if (j > 0 && array[i][j - 1] == array[i][j] + 1) {
            if (dp[i][j - 1] == 0) {
                dfs(array, dp, i, j - 1);
            }
            dp[i][j] = Math.max(dp[i][j - 1] + 1, dp[i][j]);
        }
        if (i < array.length - 1 && array[i + 1][j] == array[i][j] + 1) {
            if (dp[i + 1][j] == 0) {
                dfs(array, dp, i + 1, j);
            }
            dp[i][j] = Math.max(dp[i + 1][j] + 1, dp[i][j]);
        }
        if (j < array[0].length - 1 && array[i][j + 1] == array[i][j] + 1) {
            if (dp[i][j + 1] == 0) {
                dfs(array, dp, i, j + 1);
            }
            dp[i][j] = Math.max(dp[i][j + 1] + 1, dp[i][j]);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().findLongestContinuous(new int[][]{{7, 8, 6}, {9, 4, 5}, {2, 3, 1}}));
    }
}
