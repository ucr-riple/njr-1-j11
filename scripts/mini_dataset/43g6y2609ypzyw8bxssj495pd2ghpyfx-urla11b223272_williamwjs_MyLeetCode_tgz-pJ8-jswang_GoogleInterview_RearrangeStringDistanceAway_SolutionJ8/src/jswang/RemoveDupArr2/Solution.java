package jswang.RemoveDupArr2;

/**
 * Created by JiashuoWang on 8/26/14.
 */
public class Solution {
    public int removeDuplicates(int[] A) {
        int size = 0;
        int times;
        for (int i = 0; i < A.length;) {
            times = 1;
            A[size] = A[i];
            while (i++ < A.length-1 && A[i] == A[size]) {
                if (times != 0) {
                    A[++size] = A[i];
                    times--;
                }
            }
            size++;
        }
        return size;
    }
}