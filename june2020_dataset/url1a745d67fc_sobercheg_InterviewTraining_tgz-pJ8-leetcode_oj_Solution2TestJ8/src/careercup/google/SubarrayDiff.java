package careercup.google;

/**
 * Created by sobercheg on 11/22/13.
 * <p/>
 * http://www.careercup.com/question?id=19286747
 * Given an array of integers. Find two disjoint contiguous sub-arrays such that the absolute difference between
 * the sum of two sub-array is maximum.
 * The sub-arrays should not overlap.
 * <p/>
 * eg- [2 -1 -2 1 -4 2 8] ans - (-1 -2 1 -4) (2 8), diff = 16
 */
public class SubarrayDiff {

    public Answer getMaxSubarrayDiff(int[] array) {
        int[] lmax = new int[array.length + 1];
        int[] lmin = new int[array.length + 1];
        int[] rmax = new int[array.length + 1];
        int[] rmin = new int[array.length + 1];
        lmax[array.length] = 0;
        lmin[0] = 0;

        int currentMaxSum = 0;
        int currentMinSum = 0;

        for (int i = 0; i < array.length; i++) {
            currentMaxSum += array[i];
            if (currentMaxSum > 0) {
                lmax[i + 1] = Math.max(currentMaxSum, lmax[i]);
            } else {
                currentMaxSum = 0;
                lmax[i + 1] = lmax[i];
            }
            currentMinSum += array[i];
            if (currentMinSum < 0) {
                lmin[i + 1] = Math.min(currentMinSum, lmin[i]);
            } else {
                currentMinSum = 0;
                lmin[i + 1] = lmin[i];
            }
        }
        currentMaxSum = 0;
        currentMinSum = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            currentMaxSum += array[i];
            if (currentMaxSum > 0) {
                rmax[i] = Math.max(currentMaxSum, rmax[i + 1]);
            } else {
                currentMaxSum = 0;
                rmax[i] = rmax[i + 1];
            }
            currentMinSum += array[i];
            if (currentMinSum < 0) {
                rmin[i] = Math.min(currentMinSum, rmin[i + 1]);
            } else {
                currentMinSum = 0;
                rmin[i] = rmin[i + 1];
            }
        }
        Answer answer = new Answer();
        for (int i = 0; i <= array.length; i++) {
            if (Math.abs(lmin[i] - rmax[i]) > answer.diff) answer.diff = Math.abs(lmin[i] - rmax[i]);
            if (Math.abs(lmax[i] - rmin[i]) > answer.diff) answer.diff = Math.abs(lmax[i] - rmin[i]);
        }
        return answer;
    }

    public static void main(String[] args) {
        SubarrayDiff diff = new SubarrayDiff();
        assertEqual(new Answer(16), diff.getMaxSubarrayDiff(new int[]{2, -1, -2, 1, -4, 2, 8}), "sorry");
        assertEqual(new Answer(10), diff.getMaxSubarrayDiff(new int[]{4, -1, 7}), "sorry");
        assertEqual(new Answer(6), diff.getMaxSubarrayDiff(new int[]{-1, -2, -3}), "sorry");

    }

    static class Answer {
        int diff;

        Answer() {

        }

        Answer(int diff) {
            this.diff = diff;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Answer answer = (Answer) o;

            if (diff != answer.diff) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int result = diff;
            return result;
        }

        @Override
        public String toString() {
            return "Answer{" +
                    "diff=" + diff +
                    '}';
        }
    }

    private static void assertEqual(int expected, int actual, String message) {
        if (expected != actual) throw new IllegalStateException(message + " expected" + expected + "; actual" + actual);
    }

    private static void assertEqual(Object expected, Object actual, String message) {
        if (!expected.equals(actual))
            throw new IllegalStateException(message + " expected" + expected + "; actual" + actual);
    }
}
