package jswang.MinimumSizeSubarraySum;

/**
 * Created by willwjs on 9/1/15.
 */
public class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null) {
            return 0;
        }
        int minlen = Integer.MAX_VALUE;
        int i = 0, j = -1, sum = 0;
        while (++j < nums.length) {
            sum += nums[j];
            if (sum < s) continue;
            while (sum >= s) {
                sum -= nums[i];
                i++;
            }
            if (minlen > j - i + 2) minlen = j - i + 2;
        }

        if (minlen == Integer.MAX_VALUE)
            return 0;
        return minlen;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().minSubArrayLen(4, new int[]{1, 4, 4}));
    }
}