package jswang.ClimbingStairs;

/**
 * Created by willwjs on 11/17/14.
 */
public class Solution {
    public int climbStairs(int n) {
        int[] wayofclimb = new int[n + 1];
        if (n == 1) return 1;
        else wayofclimb[1] = 1;
        if (n == 2) return 2;
        else wayofclimb[2] = 2;

        for (int i = 3; i < n + 1; ++i)
            wayofclimb[i] = wayofclimb[i - 1] + wayofclimb[i - 2];

        return wayofclimb[n];

        /*//递归，超时
        if (n == 0) return 1;
        if (n < 0) return 0;
        return climbStairs(n - 1) + climbStairs(n - 2);*/
    }
}
