package jswang.RemoveDupArr1;

/**
 * Created by JiashuoWang on 8/26/14.
 */
public class Solution {
    public int removeDuplicates(int[] A) {
        int size = 0;
        for (int i = 0; i < A.length;) {
            A[size] = A[i];
            while (i++ < A.length-1 && A[i] == A[size]);
            size++;
        }
        return size;
    }
}