package jswang.GoogleInterview.ThreeSumSmaller;

import java.util.Arrays;

/**
 * Created by willwjs on 9/14/15.
 * http://www.1point3acres.com/bbs/thread-139553-1-1.html
 * 8/12 phone
 */
public class Solution {
    public int threeSumSmaller(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int count = 0;
        for (int a = 0; a < nums.length - 2; ++a) {
            if (a > 0 && nums[a] == nums[a - 1]) continue;
            if (nums[a] + nums[a + 1] + nums[a + 2] > target) break; //bang dai
            int b = a + 1, c = nums.length - 1;
            while (b < c) {
                int sum = nums[a] + nums[b] + nums[c];
                if (sum > target) {
                    --c;
                } else {
                    count += c - b; //bang dai
                    ++b;
                    while (b < c && nums[b] == nums[b - 1]) ++b;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().threeSumSmaller(new int[] {1, 2, 3, 4, 5}, 8));
    }
}
