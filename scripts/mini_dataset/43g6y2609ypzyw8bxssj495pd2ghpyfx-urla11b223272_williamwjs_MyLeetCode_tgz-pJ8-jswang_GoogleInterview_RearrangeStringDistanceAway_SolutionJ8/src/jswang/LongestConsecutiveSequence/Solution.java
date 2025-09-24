package jswang.LongestConsecutiveSequence;

import java.util.HashMap;

/**
 * Created by willwjs on 11/15/14.
 * HashMap的用法
 */
public class Solution {
    public int longestConsecutive(int[] num) {
        HashMap<Integer, Boolean> hashMap = new HashMap<Integer, Boolean>();
        for (int i : num)
            hashMap.put(i, false);
        int longest = 0;

        for (int i : num) {
            if (hashMap.get(i)) continue;
            int tmp = i, longi = 1;
            while (hashMap.containsKey(--tmp)) {
                hashMap.put(tmp, true);
                longi++;
            }
            tmp = i;
            while (hashMap.containsKey(++tmp)) {
                hashMap.put(tmp, true);
                longi++;
            }
            if (longest < longi) longest = longi;
        }
        return longest;
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        int[] num = {100, 3, 200, 4, 2, 300, 1};
        System.out.println(sol.longestConsecutive(num));
    }
}
