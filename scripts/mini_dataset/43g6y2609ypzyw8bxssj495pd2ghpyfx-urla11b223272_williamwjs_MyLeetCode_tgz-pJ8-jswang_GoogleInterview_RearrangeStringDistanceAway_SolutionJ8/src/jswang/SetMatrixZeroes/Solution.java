package jswang.SetMatrixZeroes;

/**
 * Created by willwjs on 11/17/14.
 * 先标记再赋值为0
 */
public class Solution {
    public void setZeroes(int[][] matrix) {
        boolean firstrow = false, firstcol = false;
        if (matrix[0][0] == 0) {
            firstrow = firstcol = true;
        }
        else {
            for (int i = 1; i < matrix.length; ++i) {
                if (matrix[i][0] == 0) {
                    firstcol = true;
                    break;
                }
            }
            for (int i = 1; i < matrix[0].length; ++i) {
                if (matrix[0][i] == 0) {
                    firstrow = true;
                    break;
                }
            }
        }
        for (int r = 1; r < matrix.length; ++r) {
            for (int c = 1; c < matrix[0].length; ++c) {
                if (matrix[r][c] == 0) {
                    matrix[0][c] = 0;
                    matrix[r][0] = 0;
                }
            }
        }

        for (int i = 1; i < matrix.length; ++i) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < matrix[0].length; ++j)
                    matrix[i][j] = 0;
            }
        }
        for (int i = 1; i < matrix[0].length; ++i) {
            if (matrix[0][i] == 0) {
                for (int j = 1; j < matrix.length; ++j)
                    matrix[j][i] = 0;
            }
        }
        if (firstrow) {
            for (int i = 0; i < matrix[0].length; ++i)
                matrix[0][i] = 0;
        }
        if (firstcol) {
            for (int i = 0; i < matrix.length; ++i)
                matrix[i][0] = 0;
        }
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        int[][] matrix = {{0,0,0,5},{4,3,1,4},{0,1,1,4},{1,2,1,3},{0,0,1,1}};
        sol.setZeroes(matrix);
        for (int[] i : matrix) {
            for (int j : i)
                System.out.print(j + " ");
            System.out.println();
        }
    }
}
