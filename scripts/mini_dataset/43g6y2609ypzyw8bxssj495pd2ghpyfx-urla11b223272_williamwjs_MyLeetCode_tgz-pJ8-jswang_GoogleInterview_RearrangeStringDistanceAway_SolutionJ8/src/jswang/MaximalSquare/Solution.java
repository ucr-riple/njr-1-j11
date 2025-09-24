package jswang.MaximalSquare;

/**
 * Created by willwjs on 8/31/15.
 */
public class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int res[][] = new int[matrix.length][matrix[0].length];
        int maxS = 0;
        for (int i = 0; i < matrix.length; ++i) {
            res[i][0] = matrix[i][0] - '0';
            if (res[i][0] == 1) maxS = 1;
        }
        for (int i = 1; i < matrix[0].length; ++i) {
            res[0][i] = matrix[0][i] - '0';
            if (res[0][i] == 1) maxS = 1;
        }

        for (int i = 1; i < matrix.length; ++i) {
            for (int j = 1; j < matrix[0].length; ++j) {
                if (matrix[i][j] == '1') {
                    int min = Math.min(Math.min(res[i][j - 1], res[i - 1][j]), res[i - 1][j - 1]);
                    res[i][j] = (int) Math.pow(Math.sqrt(min) + 1, 2);
                    if (res[i][j] > maxS) maxS = res[i][j];
                }
            }
        }

        return maxS;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().maximalSquare(new char[][] {{'0', '1'}}));
    }
}