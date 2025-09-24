package careercup.linkedin;

/**
 * Created by Sobercheg on 12/9/13.
 * http://www.careercup.com/question?id=23039666
 * Maximum value Continuous Subsequence:
 * Given array A[n] find continuous subsequence a[i]..a[j] for which sum of elements in the subsequence is maximum.
 * Ex: {-2, 11, -4, 13, -5, -2} --> 11 - 4 +13 = 20
 * {1, -3, 4, -2, -1, 6} --> 4 -2 -1 +6 = 7
 * Time complexity should O(nlogn)
 */
public class MaxSubarrayRecursive {

    class Subarray {
        int left;
        int right;
        int sum;

        Subarray(int left, int right, int sum) {
            this.left = left;
            this.right = right;
            this.sum = sum;
        }

        Subarray() {
        }

        @Override
        public String toString() {
            return "Subarray{" +
                    "left=" + left +
                    ", right=" + right +
                    ", sum=" + sum +
                    '}';
        }
    }

    public Subarray getMaxSubarray(int[] array) {
        return getMaxSubarray(array, 0, array.length - 1);
    }

    private Subarray getMaxSubarray(int[] array, int left, int right) {
        // base case
        if (left == right) return new Subarray(left, right, array[left]);

        int mid = (left + right) / 2;
        // Case 1: max subarray entirely in the left half
        Subarray leftSubarray = getMaxSubarray(array, left, mid);

        // Case 2: max subarray entirely in the right half
        Subarray rightSubarray = getMaxSubarray(array, mid + 1, right);

        // Case 3: max subarray crosses the midpoint
        Subarray crossSubarray = new Subarray();
        int leftSum = 0;
        int maxLeftSum = Integer.MIN_VALUE;
        for (int i = mid; i >= left; i--) {
            leftSum += array[i];
            if (leftSum > maxLeftSum) {
                maxLeftSum = leftSum;
                crossSubarray.left = i;
            }
        }

        int rightSum = 0;
        int maxRightSum = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= right; i++) {
            rightSum += array[i];
            if (rightSum > maxRightSum) {
                maxRightSum = rightSum;
                crossSubarray.right = i;
            }
        }

        crossSubarray.sum = maxLeftSum + maxRightSum;
        if (leftSubarray.sum > rightSubarray.sum && leftSubarray.sum > crossSubarray.sum) {
            return leftSubarray;
        } else if (rightSubarray.sum > leftSubarray.sum && rightSubarray.sum > crossSubarray.sum) {
            return rightSubarray;
        } else
            return crossSubarray;
    }

    public static void main(String[] args) {
        System.out.println(new MaxSubarrayRecursive().getMaxSubarray(new int[]{-2, -5, 6, -2, -3, 1, 5, -6}));
        System.out.println(new MaxSubarrayRecursive().getMaxSubarray(new int[]{-2, -3, 4, -1, -2, 1, 5, -3}));
    }
}
