package jswang.SingleNumber;

/**
 * Created by willwjs on 11/18/14.
 */
public class Solution {
    public int singleNumber(int[] A) {
        int tmp = A[0];
        for (int i = 1; i < A.length; ++i)
            tmp ^= A[i];
        return tmp;
    }
}
