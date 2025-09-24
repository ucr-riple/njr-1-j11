package jswang.ThreeSumClosest;

import java.util.Arrays;

/**
 * Created by willwjs on 11/15/14.
 */
public class Solution {
    private int abs (int x) {
        if (x >= 0) return x;
        else return -x;
    }

    public int threeSumClosest(int[] num, int target) {
        Arrays.sort(num);
        if (num.length < 3) throw new IllegalArgumentException("No two sum solution");
        int closestsum = target - 1000000;

        for (int i = 0; i < num.length - 2; i++) {
            if (i >= 1 && num[i] == num[i - 1]) continue;
            int j = i + 1, k = num.length - 1;
            while (j < k) {
                int sum = num[i] + num[j] + num[k];
                if (abs(sum - target) < abs(closestsum - target))
                    closestsum = sum;
                if (sum > target)
                    k--;
                else j++;
            }
        }
        return closestsum;
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        int[] num = {13,2,0,-14,-20,19,8,-5,-13,-3,20,15,20,
                5, 13, 14, -17, -7, 12, -6, 0, 20, -19, -1,
                -15,-2,8,-2,-9,13,0,-3,-18,-9,-9,-19,17,-14,
                -19,-4,-16,2,0,9,5,-7,-4,20,18,9,0,12,-1,10,
                -17,-11,16,-13,-14,-3,0,2,-18,2,8,20,-15,3,
                -13,-12,-2,-19,11,11,-10,1,1,-10,-2,12,0,17,
                -19,-7,8,-19,-17,5,-5,-10,8,0,-12,4,19,2,0,
                12,14,-9,15,7,0,-16,-5,16,-12,0,2,-16,14,18,
                12,13,5,0,5,6};
        System.out.println(sol.threeSumClosest(num, -59));
    }
}
