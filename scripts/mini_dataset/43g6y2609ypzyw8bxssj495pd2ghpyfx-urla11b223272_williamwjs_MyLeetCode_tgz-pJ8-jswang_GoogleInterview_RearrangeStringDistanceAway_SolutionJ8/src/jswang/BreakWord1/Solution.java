package jswang.BreakWord1;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by JiashuoWang on 10/12/14.
 */
public class Solution {
    public boolean wordBreak(String s, Set<String> dict) {
        boolean[] state = new boolean[s.length() + 1];
        state[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (state[j] && dict.contains(s.substring(j, i)))
                    state[i] = true;
            }
        }

        return state[s.length()];
    }

    public static void main (String[] args) {
        Set<String> dict = new HashSet<String>();
        dict.add("Leet");
        dict.add("code");
        dict.add("a");
        Solution sol = new Solution();
        System.out.println(sol.wordBreak("aLeetcode", dict));
    }
}
