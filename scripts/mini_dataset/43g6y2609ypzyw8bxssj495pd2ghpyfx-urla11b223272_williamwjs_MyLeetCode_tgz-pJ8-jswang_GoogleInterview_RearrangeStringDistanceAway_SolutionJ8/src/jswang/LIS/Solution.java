package jswang.LIS;

/**
 * Created by JiashuoWang on 10/4/14.
 * Longest Increasing Subsequence
 */
public class Solution {
    void lis (int[] s) {
        int[] result = new int[s.length];
        result[0] = 1;
        int max = 1;
        for (int i = 1; i < s.length; i++) {
            result[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (s[i] > s[j]) {
                    result[i] = result[j] + 1;
                    break;
                }
            }
            if (max < result[i]) max = result[i];
        }
        System.out.println(max);
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        int[] s = {1,2,8,9,3,7,6,4,5};
        sol.lis(s);
    }
}
