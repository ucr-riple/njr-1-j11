package jswang.WordBreak;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by willwjs on 8/16/15.
 */
public class Solution {
    public boolean wordBreak(String s, Set<String> wordDict) {
        boolean flag[] = new boolean[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            flag[i] = flag[i] || wordDict.contains(s.substring(0, i + 1));
            for (int j = i + 1; j < s.length(); ++j) {
                flag[j] = flag[j] || (flag[i] && wordDict.contains(s.substring(i + 1, j + 1)));
            }
        }
        return flag[s.length() - 1];
    }


    public boolean wordBreak2(String s, Set<String> wordDict) {
        int length = s.length();
        boolean[] dp = new boolean[length + 1];
        dp[0] = true;
        for (int i = 1; i <= length; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[length];
    }

    public static void main(String[] args) {
        Set<String> set = new HashSet<String>();
        set.add("dog");
        set.add("s");
        set.add("gs");
        System.out.println(new Solution().wordBreak("dogs", set));
    }
}