package jswang.ContainsDuplicate3;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by willwjs on 9/1/15.
 */
public class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length < 2 || k < 1 || t < 0) {
            return false;
        }

        SortedSet<Long> set = new TreeSet<Long>();
        for (int i = 0; i < nums.length; ++i) {
            SortedSet<Long> subset = set.subSet((long) nums[i] - t, (long) nums[i] + t + 1);
            if (!subset.isEmpty()) return true;
            if (i >= k) {
                set.remove((long) nums[i - k]);
            }
            set.add((long) nums[i]);
        }

        return false;
    }
}