package careercup.linkedin;

/**
 * Created by Sobercheg on 12/9/13.
 */
public class MaxSubarrayKadane {

    public int getMaxSubarray(int[] array) {
        int maxSum = array[0];
        int currentSum = 0;
        int startIndex = 0;
        int maxIndex = 0;
        int maxStartIndex = 0;

        for (int i = 0; i < array.length; i++) {
            currentSum += array[i];
            if (currentSum < 0) {
                currentSum = 0;
                startIndex = i + 1;
            } else {
                if (currentSum > maxSum) {
                    maxIndex = i;
                    maxStartIndex = startIndex;
                }
            }
        }
        return maxSum;
    }
}
