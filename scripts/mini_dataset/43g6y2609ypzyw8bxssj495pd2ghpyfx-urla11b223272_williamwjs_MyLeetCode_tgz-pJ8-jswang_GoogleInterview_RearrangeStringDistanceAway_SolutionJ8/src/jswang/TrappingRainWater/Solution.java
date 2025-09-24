package jswang.TrappingRainWater;

/**
 * Created by willwjs on 11/16/14.
 */
public class Solution {
    public int trap(int[] A) {
        int[] maxLeft = new int[A.length];
        int[] maxRight = new int[A.length];
        int tmpMax = 0, totalV = 0;

        for (int i = 0; i < A.length; ++i) {
            if (A[i] > tmpMax) tmpMax = A[i];
            maxLeft[i] = tmpMax;
        }
        tmpMax = 0;
        for (int i = A.length - 1; i >= 0; --i) {
            if (A[i] > tmpMax) tmpMax = A[i];
            maxRight[i] = tmpMax;
        }

        for (int i = 0; i < A.length; ++i) {
            if (maxLeft[i] < maxRight[i])
                totalV += maxLeft[i] - A[i];
            else
                totalV += maxRight[i] - A[i];
        }

        return totalV;

        /*//O(n^2)
        int i = 0, totalV = 0;

        while (i < A.length) {
            int tmpV = 0, tmpA = A[i], j = i + 1;
            if (tmpA == 0) {
                ++i;
                continue;
            }
            for (; tmpA > 0; --tmpA) {
                for (j = i + 1, tmpV = 0; j < A.length; ++j) {
                    if (A[j] >= tmpA) {
                        totalV += tmpV;
                        i = j;
                        break;
                    } else
                        tmpV += tmpA - A[j];
                }
                if (j != A.length) break;
            }
            if (j == A.length) ++i;
        }

        return totalV;*/
    }

    public static void main (String[] args) {
        int[] A = {3, 1, 2};
        Solution sol = new Solution();
        System.out.println(sol.trap(A));
    }
}
