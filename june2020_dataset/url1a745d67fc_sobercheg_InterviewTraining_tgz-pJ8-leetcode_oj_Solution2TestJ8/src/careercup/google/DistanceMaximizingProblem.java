package careercup.google;

/**
 * Created by Sobercheg on 12/8/13.
 * <p/>
 * http://www.careercup.com/question?id=11532811
 * Given an array A[], find (i, j) such that A[i] < A[j] and (j - i) is maximum.
 * <p/>
 * Linear solution: http://askmecode.wordpress.com/2012/07/16/a-distance-maximizing-problem-ii/
 * Solution explanations: http://leetcode.com/2011/05/a-distance-maximizing-problem.html#comment-1122
 */
public class DistanceMaximizingProblem {

    static class MaxDistance {
        int i;
        int j;

        @Override
        public String toString() {
            return "MaxDistance{" +
                    "i=" + i +
                    ", j=" + j +
                    '}';
        }
    }

    public MaxDistance findMaxDistance(int[] array) {
        MaxDistance maxDist = new MaxDistance();
        int currentMax = array[0];
        int n = array.length;
        boolean[] startPoint = new boolean[n];
        for (int i = 1; i < n; i++) {
            if (array[i] < currentMax) {
                currentMax = array[i];
                startPoint[i - 1] = true;
            }
        }

        int i = n - 1;
        int j = n - 1;
        int max = 0;

        while (i >= 0) {
            if (!startPoint[i]) {
                i--;
                continue;
            }

            while (array[j] <= array[i] && j > i) {
                j--;
            }

            if (j - i > max) {
                maxDist.i = i;
                maxDist.j = j;
                max = j - i;
            }
            i--;

        }

        return maxDist;
    }

    public static void main(String[] args) {
        DistanceMaximizingProblem dmp = new DistanceMaximizingProblem();
        System.out.println(dmp.findMaxDistance(new int[]{4, 3, 5, 2, 1, 3, 2, 3}));
    }
}
