package jswang.GasStation;

/**
 * Created by willwjs on 11/17/14.
 * 全局考虑算法，对sum的设计很巧妙
 */
public class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0, start = -1;

        for (int i = 0, sum = 0; i < gas.length; ++i) {
            total += gas[i] - cost[i];
            sum += gas[i] - cost[i];
            if (sum < 0) {
                start = i;
                sum = 0;
            }
        }

        if (total >= 0)
            return start + 1;
        else return -1;
    }

    public static void main (String[] args) {
        int[] gas = {4};
        int[] cost = {5};
        Solution sol = new Solution();
        System.out.println(sol.canCompleteCircuit(gas, cost));
    }
}
