package jswang.RotateImage;

/**
 * Created by willwjs on 11/16/14.
 * matrix[f(x)][g(y)]中，f(x)和g(y)一个是i的函数，一个是a的函数
 */
public class Solution {
    public void rotate(int[][] matrix) {
        for (int a = 0, b = 0; a < matrix.length / 2; ++a, ++b) {
            for (int i = a; i < matrix.length - 1 - a; ++i) {
                int tmp = matrix[a][i];
                matrix[a][i] = matrix[matrix.length - 1 - i][a];
                matrix[matrix.length - 1 - i][a] = matrix[matrix.length - 1 - a][matrix.length - 1 - i];
                matrix[matrix.length - 1 - a][matrix.length - 1 - i] = matrix[i][matrix.length - 1 - a];
                matrix[i][matrix.length - 1 - a] = tmp;
            }
        }
    }
}
