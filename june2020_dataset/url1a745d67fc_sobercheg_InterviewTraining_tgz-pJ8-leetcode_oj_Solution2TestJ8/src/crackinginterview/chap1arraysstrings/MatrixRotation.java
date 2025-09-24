package crackinginterview.chap1arraysstrings;

import java.util.Arrays;

/**
 * Created by Sobercheg on 12/6/13.
 */
public class MatrixRotation {

    public void rotateClockwise(int[][] a) {
        int n = a.length;
        if (n != a[0].length) throw new IllegalArgumentException("Non-square matrix");
        if (n == 1) return;

        for (int layer = 0; layer < n / 2; layer++) {
            for (int start = layer; start < n - layer - 1; start++) {
                int tmp = a[layer][start];
                a[layer][start] = a[n - layer - start - 1][layer];
                a[n - layer - start - 1][layer] = a[n - layer - 1][n - layer - start - 1];
                a[n - layer - 1][n - layer - start - 1] = a[layer + start][n - layer - 1];
                a[layer + start][n - layer - 1] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        MatrixRotation rotation = new MatrixRotation();
        int[][] array = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        rotation.rotateClockwise(array);
        for (int[] row : array)
            System.out.println(Arrays.toString(row));
    }
}
