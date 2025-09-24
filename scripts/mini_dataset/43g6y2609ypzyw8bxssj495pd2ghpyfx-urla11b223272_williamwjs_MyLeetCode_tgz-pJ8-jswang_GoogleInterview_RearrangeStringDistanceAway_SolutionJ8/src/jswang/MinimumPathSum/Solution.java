package jswang.MinimumPathSum;

/**
 * Created by willwjs on 9/7/15.
 */
public class Solution {
    public int minPathSum(int[][] grid) {
        int[][] res = new int[grid.length][grid[0].length];
        res[0][0] = grid[0][0];
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (i == 0 && j == 0) continue;
                int left = -1, up = -1;
                if (i > 0) up = res[i - 1][j];
                if (j > 0) left = res[i][j - 1];
                if (up < 0) res[i][j] = left + grid[i][j];
                else if (left < 0) res[i][j] = up + grid[i][j];
                else res[i][j] = grid[i][j] + (up < left ? up : left);
            }
        }
        return res[grid.length - 1][grid[0].length - 1];
    }

    public static void main(String [] args) {
        System.out.println(new Solution().minPathSum(new int[][] {{1, 2}, {1, 1}}));
    }
}