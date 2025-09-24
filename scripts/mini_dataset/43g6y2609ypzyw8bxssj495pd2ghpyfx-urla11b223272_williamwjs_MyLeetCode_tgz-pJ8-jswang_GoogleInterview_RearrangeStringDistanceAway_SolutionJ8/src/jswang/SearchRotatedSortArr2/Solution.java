package jswang.SearchRotatedSortArr2;

/**
 * Created by willwjs on 11/6/14.
 * 时间复杂度O(N)，于是可以清除掉所有冗余转化为SearchRotatedSortArr1
 */
public class Solution {
    public boolean search(int[] A, int target) {
        int left = 0, right = A.length - 1, mid;

        while (A[left] == A[right] && left < right) right--;

        while (left <= right) {
            while (left + 1 < right && A[left] == A[left + 1]) left++;
            while (right - 1 > left && A[right] == A[right - 1]) right--;
            mid = (left + right) / 2;
            if (A[mid] == target) return true;
            if (left == right - 1) return A[right] == target ? true : false;
            if (A[left] < A[mid]) {
                if (A[left] <= target && target < A[mid])
                    right = mid - 1;
                else left = mid + 1;
            }
            else {
                if (A[right] >= target && target >= A[mid])
                    left = mid + 1;
                else
                    right = mid - 1;
            }
        }

        return false;
    }

    public static void main (String[] args) {
        int[] A = {2,2,2,0,2,2};
        Solution sol = new Solution();

        System.out.println(sol.search(A, 0));
    }
}