package jswang.GoogleInterview.FindPeak;

/**
 * Created by willwjs on 10/7/15.
 * 给一个非严格先递增后递减的数组，找到最大值
 * http://www.1point3acres.com/bbs/thread-143395-1-1.html
 */
public class Solution {
    public int findPeak(int[] nums) {
        if (nums == null || nums.length == 0) throw new RuntimeException();
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            if (left == right) {
                return nums[left];
            }
            if (left == right - 1) {
                return nums[left] > nums[right] ? nums[left] : nums[right];
            }
            int mid = (left + right) / 2;
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return nums[mid];
            } else if (nums[mid] > nums[mid - 1] && nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else if (nums[mid] < nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                right = mid - 1;
            } else {
                int midLeft = 0, midRight = 0;

                if (nums[mid] == nums[mid - 1]) {
                    int l = left, r = mid - 1;
                    while (l <= r) {
                        if (l == r) {
                            midLeft = l;
                            break;
                        }
                        if (l == r - 1) {
                            midLeft = nums[r] == nums[mid] ? l : r;
                            break;
                        }
                        int m = (l + r) / 2;
                        if (nums[m] == nums[mid]) {
                            r = m - 1;
                        } else {
                            l = m + 1;
                        }
                    }
                } else {
                    midLeft = mid - 1;
                }

                if (nums[mid] == nums[mid + 1]) {
                    int l = mid + 1, r = right;
                    while (l <= r) {
                        if (l == r) {
                            midRight = l;
                            break;
                        }
                        if (l == r - 1) {
                            midRight = nums[l] == nums[mid] ? r : l;
                            break;
                        }
                        int m = (l + r) / 2;
                        if (nums[m] == nums[mid]) {
                            l = m + 1;
                        } else {
                            r = m - 1;
                        }
                    }
                } else {
                    midRight = mid + 1;
                }

                if (nums[mid] == nums[midLeft]) {
                    if (nums[mid] < nums[midRight]) {
                        left = midRight;
                    } else {
                        right = midLeft;
                    }
                } else if (nums[mid] == nums[midRight]) {
                    if (nums[mid] < nums[midLeft]) {
                        right = midLeft;
                    } else {
                        left = midRight;
                    }
                } else if (nums[mid] > nums[midLeft] && nums[mid] > nums[midRight]) {
                    return nums[mid];
                } else if (nums[mid] > nums[midLeft] && nums[mid] < nums[midRight]) {
                    left = midRight;
                } else if (nums[mid] < nums[midLeft] && nums[mid] > nums[midRight]) {
                    right = midLeft;
                }
            }
        }
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        System.out.println(new Solution().findPeak(new int[] {3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,1}));
    }
}
