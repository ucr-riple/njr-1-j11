package jswang.SearchRotatedSortArr1;

/**
 * Created by willwjs on 11/6/14.
 * 将只有两个元素的情况单独考虑，即if (left == right - 1)
 * 这样可以简化具体条件的判断，以免等号考虑错了造成错误的结果
 */
public class Solution {
    private int searchin(int[] A, int left, int right, int target) {
        int mid = (left + right) / 2;
        if (A[mid] == target) return mid;
        if (left == right) return -1;
        if (left == right - 1) return A[right] == target ? right : -1;
        if (A[mid] > A[mid - 1]) {
            return searchin(A, left, mid - 1, target) >= searchin(A, mid, right, target)
                    ? searchin(A, left, mid - 1, target) : searchin(A, mid, right, target);
        }
        else {
            if (target > A[right]) return searchin(A, left, mid - 1, target);
            else return searchin(A, mid, right, target);
        }
    }

    public int search(int[] A, int target) {
        //return searchin(A, 0, A.length - 1, target);
        int left = 0, right = A.length - 1, mid;

        while (left <= right) {
            mid = (left + right) / 2;
            if (A[mid] == target) return mid;
            if (left == right - 1) return A[right] == target ? right : -1;
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

        return -1;
    }

    public static void main (String[] args) {
        int[] A = {5, 1, 3};
        Solution sol = new Solution();

        System.out.println(sol.search(A, 3));
    }
}