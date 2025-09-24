package jswang.RemoveElement;

/**
 * Created by willwjs on 11/15/14.
 */
public class Solution {
    public int removeElement(int[] A, int elem) {
        int newlength = 0;
        for (int i = 0; i < A.length; i++) {
            A[newlength] = A[i];
            if (A[i] != elem)
                newlength++;
        }
        return newlength;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] A = {4, 5};
        int newlength = sol.removeElement(A, 4);
        for (int i = 0; i < newlength; i++)
            System.out.print(A[i]);
        System.out.println();
    }
}
