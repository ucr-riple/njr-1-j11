package crackinginterview;

/**
 * Created by Sobercheg on 11/10/13.
 */
public class MaxSubarrayDiff {

    static class Subarray {
        int left;
        int right;
        int sum;

        @Override
        public String toString() {
            return "Subarray{" +
                    "left=" + left +
                    ", right=" + right +
                    ", sum=" + sum +
                    '}';
        }
    }

    public Subarray getMaxSubarray(int[] arr) {
        Subarray subarray = new Subarray();
        int absoluteLowest = 0;
        subarray.sum = Integer.MIN_VALUE;
        int diff;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < arr[absoluteLowest]) {
                absoluteLowest = i;
            }

            diff = arr[i] - arr[absoluteLowest];
            if (diff > subarray.sum) {
                subarray.sum = diff;
                subarray.left = absoluteLowest;
                subarray.right = i;
            }
        }
        return subarray;
    }

    public static void main(String[] args) {
        MaxSubarrayDiff subarray = new MaxSubarrayDiff();
        System.out.println(subarray.getMaxSubarray(new int[]{-1, 1, -3, 1}));
    }
}
