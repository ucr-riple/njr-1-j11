package jswang.Sqrt;

/**
 * Created by willwjs on 9/7/15.
 */
public class Solution {
    public int mySqrt(int x) {
        int i = 1, j = x / 2 + 1;
        while (i <= j) {
            int mid = (i + j) / 2, res = x / mid;
            if (mid <= res && mid + 1 > x / (mid + 1)) return mid;
            if (res < mid) {
                j = mid - 1;
            } else {
                i = mid + 1;
            }
        }
        return 0;
    }

    public static void main(String [] args) {
        System.out.println(new Solution().mySqrt(10));
    }
}