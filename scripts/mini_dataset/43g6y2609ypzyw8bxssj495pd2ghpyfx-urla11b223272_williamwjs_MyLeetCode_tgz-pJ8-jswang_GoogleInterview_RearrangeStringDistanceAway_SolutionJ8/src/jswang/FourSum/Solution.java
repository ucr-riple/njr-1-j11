package jswang.FourSum;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by willwjs on 11/15/14.
 */
public class Solution {
    public List<List<Integer>> fourSum(int[] num, int target) {
        Arrays.sort(num);
        List<List<Integer>> lists = new LinkedList<List<Integer>>();

        for (int a = 0; a < num.length - 2; a++) {
            if (a >= 1 && num[a] == num[a - 1]) continue;
            for (int b = a + 1; b < num.length - 1; b++) {
                if (b >= a + 2 && num[b] == num[b - 1]) continue;
                int c = b + 1, d = num.length - 1;
                while (c < d) {
                    int sum = num[a] + num[b] + num[c] +num[d];
                    if (sum > target)
                        d--;
                    else if (sum < target)
                        c++;
                    else {
                        List<Integer> list = new LinkedList<Integer>();
                        list.add(num[a]);
                        list.add(num[b]);
                        list.add(num[c]);
                        list.add(num[d]);
                        lists.add(list);
                        d--;
                        while (c < d && num[d] == num[d + 1]) d--;
                        c++;
                        while (c < d && num[c] == num[c - 1]) c++;
                    }
                }
            }
        }
        return lists;
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        int[] num = {0, 0, 0, 0};
        List<List<Integer>> list = sol.fourSum(num, 0);
    }
}
