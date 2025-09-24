package jswang.GoogleInterview.MaximizeLeft0Right1;

/**
 * Created by willwjs on 10/11/15.
 */
public class Solution {
    public int findSplit(int[] arr) {
        int right0 = 0, right1 = 0;
        for (int i : arr) {
            if (i == 0) right0++;
            else right1++;
        }
        int left0 = 0, left1 = 0, max = 0;
        for (int i : arr) {
            if (i == 0) right0--;
            else right1--;
            max = Math.max(max, left0 + right1);
            if (i == 0)
                left0++;
            else left1++;
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().findSplit(new int[] {0, 1, 1, 1}));
    }
}
