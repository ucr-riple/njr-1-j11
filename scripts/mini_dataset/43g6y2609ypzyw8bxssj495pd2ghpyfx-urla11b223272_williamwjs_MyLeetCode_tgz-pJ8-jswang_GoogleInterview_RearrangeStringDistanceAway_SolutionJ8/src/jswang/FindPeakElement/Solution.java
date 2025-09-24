package jswang.FindPeakElement;

/**
 * Created by willwjs on 5/14/15.
 * 1.Peak是极值,不是最大值
 * 2.注意二分法区间是和low与high有关的,而非0和length-1
 * 3.二分法最终考虑只有1个,只有2个的临界情况
 */
public class Solution {
    public int findPeakElement(int[] nums) {
        if (nums.length == 0)
            throw new RuntimeException();
        return findPeakHelper(nums, 0, nums.length - 1);
    }

    private int findPeakHelper(int[] nums, int low, int high) {
        if (low == high) return low;
        if (low == high - 1)
            return nums[low] > nums[high] ? low : high;
        int mid = (low + high) / 2;
        if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1])
            return mid;
        if (nums[mid] > nums[mid - 1])
            return findPeakHelper(nums, mid + 1, high);
        else
            return findPeakHelper(nums, low, mid - 1);
    }

    public static void main(String[] args) {
        System.out.println(new Solution().findPeakElement(new int[] {1,2}));
    }
}