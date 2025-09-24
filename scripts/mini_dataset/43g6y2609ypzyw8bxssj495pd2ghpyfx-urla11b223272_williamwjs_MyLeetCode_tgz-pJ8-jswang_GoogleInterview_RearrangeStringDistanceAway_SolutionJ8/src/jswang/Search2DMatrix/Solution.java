package jswang.Search2DMatrix;

/**
 * Created by willwjs on 9/6/15.
 */
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int i = 0, j = matrix.length - 1, row = 0;
        while (i <= j) {
            int mid = (i + j) / 2;
            if (matrix[mid][0] == target) return true;
            if (matrix[mid][0] > target) {
                j = mid - 1;
            }
            else {
                row = mid;
                i = mid + 1;
            }
        }

        i = 0;
        j = matrix[row].length - 1;
        while (i <= j) {
            int mid = (i + j) / 2;
            if (matrix[row][mid] == target) return true;
            if (matrix[row][mid] > target) {
                j = mid - 1;
            } else {
                i = mid + 1;
            }
        }

        return false;
    }

    public static void main(String [] args) {
        System.out.println(new Solution().searchMatrix(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 50}}, 11));
    }
}