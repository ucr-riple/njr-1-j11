package other;

/**
 * Created by Sobercheg on 11/18/13.
 */
public class MaxSubarrayPractice {
    class Subarray {
        int startIndex;
        int endIndex;
        int sum;

        @Override
        public String toString() {
            return "Subarray{" +
                    "startIndex=" + startIndex +
                    ", endIndex=" + endIndex +
                    ", sum=" + sum +
                    '}';
        }
    }

    public Subarray findMaxSubarray(int[] array) {
        Subarray subarray = new Subarray();
        int currSum = 0;
        int startIndex = 0;

        for (int i = 0; i < array.length; i++) {
            currSum += array[i];
            if (currSum <= 0) {
                currSum = 0;
                startIndex = i + 1;
            } else if (currSum > subarray.sum) {
                subarray.sum = currSum;
                subarray.startIndex = startIndex;
                subarray.endIndex = i;
            }

        }
        return subarray;
    }

    public static void main(String[] args) {
        MaxSubarrayPractice maxSubarray = new MaxSubarrayPractice();
        System.out.println(maxSubarray.findMaxSubarray(new int[]{-2, 11, -4, 13, -5, -2}));
        System.out.println(maxSubarray.findMaxSubarray(new int[]{1, -3, 4, -2, -1, 6}));
    }
}
