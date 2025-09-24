package jswang.MedianTwoSortArr;

/**
 * Created by willwjs on 11/13/14.
 */
public class Solution {
    private double findKthSortedArrays(int A[], int B[], int starta, int startb, int k){
        if ((A.length - starta) > (B.length - startb)) //Assume A is shorter
            return findKthSortedArrays(B, A, startb, starta, k);
        if (k == 1) {
            if (A.length - starta == 0) //Notice!!
                return B[startb];
            else
                return A[starta] < B[startb] ? A[starta] : B[startb];
        }
        int ka = k / 2;
        if (A.length - starta < ka)
            ka = A.length - starta;
        int kb = k - ka;
        if (ka == 0) return B[startb + kb - 1];
        if (A[starta + ka - 1] < B[startb + kb - 1])
            return findKthSortedArrays(A, B, starta + ka, startb, k - ka);
        else
            return findKthSortedArrays(A, B, starta, startb + kb, k - kb);
    }

    public double findMedianSortedArrays(int A[], int B[]) {
        int num = A.length + B.length;
        if (num == 1) return A.length == 1 ? A[0] : B[0]; //Notice!
        if (num % 2 == 0)
            return (findKthSortedArrays(A, B, 0, 0, num / 2)
                    + findKthSortedArrays(A, B, 0, 0, num / 2 + 1)) / 2.0;
        else
            return findKthSortedArrays(A, B, 0, 0, (num + 1) / 2);
    }
}
