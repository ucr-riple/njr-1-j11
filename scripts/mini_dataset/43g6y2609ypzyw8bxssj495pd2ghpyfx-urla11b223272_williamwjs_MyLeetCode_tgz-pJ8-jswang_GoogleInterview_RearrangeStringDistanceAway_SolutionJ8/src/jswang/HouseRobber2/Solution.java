package jswang.HouseRobber2;

/**
 * Created by willwjs on 5/21/15.
 * 两次DP,一次去头,一次去尾
 */
public class Solution {
    public int rob(int[] nums) {
        if (nums.length == 0)
            return 0;
        if (nums.length == 1)
            return nums[0];
        if (nums.length == 2)
            return nums[0] > nums[1] ? nums[0] : nums[1];
        if (nums.length == 3) {
            int tmp = nums[0] > nums[1] ? nums[0] : nums[1];
            return tmp > nums[2] ? tmp : nums[2];
        }
        int local1 = nums[0], local2 = nums[1], local3 = nums[0] + nums[2];
        int globalMax1 = local2 > local3 ? local2 : local3;
        for (int i = 3; i < nums.length - 1; ++i) {
            int tmp = local1 > local2 ? local1 + nums[i] : local2 + nums[i];
            globalMax1 = globalMax1 > tmp ? globalMax1 : tmp;
            local1 = local2;
            local2 = local3;
            local3 = tmp;
        }
        local1 = nums[1];
        local2 = nums[2];
        local3 = nums[1] + nums[3];
        int globalMax2 = local2 > local3 ? local2 : local3;
        for (int i = 4; i < nums.length; ++i) {
            int tmp = local1 > local2 ? local1 + nums[i] : local2 + nums[i];
            globalMax2 = globalMax2 > tmp ? globalMax2 : tmp;
            local1 = local2;
            local2 = local3;
            local3 = tmp;
        }
        return globalMax1 > globalMax2 ? globalMax1 : globalMax2;
    }
}