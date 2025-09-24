package careercup.linkedin;

/**
 * Created by Sobercheg on 12/9/13.
 * http://www.careercup.com/question?id=5140428923863040
 * <p/>
 * You have two arrays of integers, where the integers do not repeat and the two arrays have no common integers.
 * Let x be any integer in the first array, y any integer in the second. Find min(Abs(x-y)). That is, find the smallest difference between any of the integers in the two arrays.
 * Assumptions: Assume both arrays are sorted in ascending order.
 */
public class MinDiffInSortedArrays {

    public int getMinDiff(int[] array1, int[] array2) {
        int minDiff = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;
        int m = array1.length;
        int n = array2.length;

        while (i < m && j < n) {
            // move both indices to keep values from both arrays in as small window as possible
            while (array1[i] < array2[j]) {
                int diff = array2[j] - array1[i];
                i++;
                if (diff < minDiff) minDiff = diff;
                if (i == m) return minDiff;
            }

            while (array1[i] > array2[j]) {
                int diff = array1[i] - array2[j];
                j++;
                if (diff < minDiff) minDiff = diff;
                if (j == n) return minDiff;
            }
        }

        return minDiff;
    }

    public static void main(String[] args) {
        System.out.println(new MinDiffInSortedArrays().getMinDiff(new int[]{1, 5, 10, 11}, new int[]{8, 15}));
    }
}
