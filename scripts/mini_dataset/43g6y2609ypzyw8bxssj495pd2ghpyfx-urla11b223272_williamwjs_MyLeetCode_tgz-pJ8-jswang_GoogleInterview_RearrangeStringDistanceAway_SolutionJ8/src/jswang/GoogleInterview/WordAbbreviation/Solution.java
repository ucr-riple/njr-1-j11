package jswang.GoogleInterview.WordAbbreviation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by willwjs on 9/21/15.
 * Word Abbreviation 单词中连续的字符串可以用它的长度代替， 例如 abbreviation 可以为 a1breviation a2reviation 1bbreviation abbrevia4 ab3via4 abbreviation 11n 12等
 * 问（1）一共有多少种缩写（2）输出所有这些缩写
 * http://www.1point3acres.com/bbs/thread-140228-1-1.html
 * 8.26 onsite
 */
public class Solution {
    public List<String> wordAbbreviation(String s) {
        lists = new LinkedList<>();
        cache = new List[s.length() + 1];
        cache[0] = new LinkedList<>();
        cache[0].add("");
        for (int i = 1; i <= s.length(); ++i) {
            cache[i] = new LinkedList<>();
            for (int j = 0; j < i; ++j) {
                for (String k : cache[j]) {
                    cache[i].add(k + (i - j));
                }
            }
        }
        for (int i = 0; i < s.length(); ++i) {
            for (int j = i + 1; j <= s.length(); ++j) {
                helper(s, i, j);
            }
        }
        return lists;
    }

    private List<String> lists;
    private List<String>[] cache;

    private void helper(String s, int i, int j) {
        String ini = s.substring(0, i);
        int length = j - i;
        for (String k : cache[length]) {
            String res = ini + k + s.substring(j, s.length());
            lists.add(res);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().wordAbbreviation("abcd"));
    }
}
