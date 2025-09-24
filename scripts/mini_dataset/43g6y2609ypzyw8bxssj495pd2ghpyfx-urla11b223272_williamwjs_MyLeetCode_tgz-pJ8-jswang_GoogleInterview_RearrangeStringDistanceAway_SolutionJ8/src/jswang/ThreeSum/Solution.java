package jswang.ThreeSum;

import java.util.*;

/**
 * Created by willwjs on 11/15/14.
 */
public class Solution {
    public List<List<Integer>> threeSum(int[] num) {
        Arrays.sort(num);
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        if (num.length < 3) return list;

        for (int i = 0; i < num.length - 2; i++) {
            if (i >= 1 && num[i] == num[i - 1]) continue;
            int j = i + 1, k = num.length - 1;
            while (j < k) {
                if (num[i] + num[j] + num[k] > 0) k--;
                else if (num[i] + num[j] + num[k] < 0) j++;
                else {
                    List<Integer> tmp = new LinkedList<Integer>();
                    tmp.add(num[i]);
                    tmp.add(num[j]);
                    tmp.add(num[k]);
                    list.add(tmp);
                    j++;
                    while (j < k && num[j] == num[j - 1]) j++;
                    k--;
                    while (j < k && num[k] == num[k + 1]) k--;
                }
            }
        }
        return list;

        /*//会超时
        Arrays.sort(num);
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < num.length; i++) {
            for (int j = i + 1; j < num.length; j++) {
                if (set.contains(0 - num[i] - num[j])) {
                    List<Integer> tmp = new LinkedList<Integer>();
                    tmp.add(0 - num[i] - num[j]);
                    tmp.add(num[i]);
                    tmp.add(num[j]);
                    list.add(tmp);
                }
            }
            set.add(num[i]);
        }
        return list;*/
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        int[] num = {0, 0, 0, 0, -1, 1, 3, -2};
        List<List<Integer>> list = sol.threeSum(num);
        System.out.println(list);
    }
}
