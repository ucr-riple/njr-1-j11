package jswang.SingleNumber2;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by willwjs on 11/18/14.
 */
public class Solution {
    public int singleNumber(int[] A) {
        Set<Integer> set = new HashSet<Integer>();
        int tmp = A[0];
        set.add(A[0]);
        for (int i = 1; i < A.length; ++i) {
            if (set.contains(A[i]))
                set.remove(A[i]);
            else {
                tmp ^= A[i];
                set.add(A[i]);
            }
        }
        return tmp;
    }

    public int singleNumber2(int[] A) {
        int[] bitNum = new int[32];

        for (int i = 0; i < A.length; ++i) {
            for (int j = 0; j < 32; ++j) {
                bitNum[j] += (A[i] >> j) & 1;
                bitNum[j] %= 3;
            }
        }

        int tmp = 0;
        for (int i = 0; i < 32; ++i) {
            tmp += bitNum[i] << i;
        }
        return tmp;
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        int[] A = {3, 2, 3, 1, 2, 3, 2};
        System.out.println(sol.singleNumber(A));
    }
}
