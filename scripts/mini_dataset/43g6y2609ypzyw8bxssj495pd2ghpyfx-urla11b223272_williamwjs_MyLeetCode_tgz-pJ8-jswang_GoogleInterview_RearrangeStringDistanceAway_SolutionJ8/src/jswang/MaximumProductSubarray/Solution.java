package jswang.MaximumProductSubarray;

/**
 * Created by willwjs on 5/14/15.
 * 记录局部最小和局部最大,用局部最小*当前值,局部最大*当前值,当前值来更新局部最小和局部最大
 */
public class Solution {
    public int maxProduct(int[] nums) {
        if (nums.length == 0)
            throw new RuntimeException();
        int globalMax = nums[0], localMax = nums[0], localMin = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            int tmpMin = localMin * nums[i];
            int tmpMax = localMax * nums[i];
            localMin = tmpMin < nums[i] ? tmpMin : nums[i];
            if (tmpMax < localMin)
                localMin = tmpMax;
            localMax = tmpMax > nums[i] ? tmpMax : nums[i];
            if (tmpMin > localMax)
                localMax = tmpMin;
            if (localMax > globalMax)
                globalMax = localMax;
        }
        return globalMax;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().maxProduct(new int[]{-1,2,-3,4,-5}));
    }
}