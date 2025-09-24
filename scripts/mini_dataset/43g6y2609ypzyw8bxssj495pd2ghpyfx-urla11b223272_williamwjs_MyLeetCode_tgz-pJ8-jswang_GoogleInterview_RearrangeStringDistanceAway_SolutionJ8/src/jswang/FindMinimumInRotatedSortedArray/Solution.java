package jswang.FindMinimumInRotatedSortedArray;

/**
 * Created by willwjs on 5/14/15.
 */
public class Solution {
    public int findMin(int[] nums) {
        if (nums.length == 0)
            throw new RuntimeException();
        return findMinHelper(nums, 0, nums.length - 1);
    }

    private int findMinHelper(int[] nums, int left, int right) {
        if (left == right)
            return nums[left];
        if (left == right - 1)
            return nums[left] < nums[right] ? nums[left] : nums[right];
        int mid = (left + right) / 2;
        if (nums[mid] > nums[right])
            return findMinHelper(nums, mid + 1, right);
        else
            return findMinHelper(nums, left, mid);
    }
}