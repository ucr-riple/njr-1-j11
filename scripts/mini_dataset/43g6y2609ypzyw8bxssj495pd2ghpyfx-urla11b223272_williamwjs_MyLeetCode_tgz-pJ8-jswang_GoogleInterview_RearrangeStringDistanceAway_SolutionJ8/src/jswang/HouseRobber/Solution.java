package jswang.HouseRobber;

/**
 * Created by willwjs on 5/16/15.
 */
public class Solution {
    public int rob(int[] num) {
        if (num.length == 0)
            return 0;
        if (num.length == 1)
            return num[0];
        if (num.length == 2)
            return num[0] > num[1] ? num[0] : num[1];
        int[] res = new int[num.length];
        res[0] = num[0];
        res[1] = num[1];
        res[2] = num[2] + num[0];
        int max = res[2] > res[1] ? res[2] : res[1];
        for (int i = 3; i < num.length; ++i) {
            res[i] = res[i - 2] > res[i - 3] ? res[i - 2] + num[i] : res[i - 3] + num[i];
            max = max > res[i] ? max : res[i];
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().rob(new int[] {2,7,9,3,1}));
    }
}