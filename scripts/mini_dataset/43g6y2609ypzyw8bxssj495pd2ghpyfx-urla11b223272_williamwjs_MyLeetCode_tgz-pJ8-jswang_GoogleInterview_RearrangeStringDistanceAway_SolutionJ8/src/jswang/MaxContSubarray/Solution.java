package jswang.MaxContSubarray;

/**
 * Created by JiashuoWang on 10/4/14.
 */
public class Solution {
    void mcSub (int[] s) {
        int[] result = new int[s.length];
        result[0] = s[0];
        for (int i = 1; i < s.length; i++) {
            result[i] = (result[i - 1] + s[i] > s[i]) ? result[i - 1] + s[i] : s[i];
        }
        int max = 0;
        for (int i : result)
            if (max < i) max = i;
        System.out.println(max);
        /*int min = 10000000;
        for (int i : result)
            if (min > i) min = i;
        System.out.println(min);*/
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        int[] s = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        sol.mcSub(s);
    }
}
