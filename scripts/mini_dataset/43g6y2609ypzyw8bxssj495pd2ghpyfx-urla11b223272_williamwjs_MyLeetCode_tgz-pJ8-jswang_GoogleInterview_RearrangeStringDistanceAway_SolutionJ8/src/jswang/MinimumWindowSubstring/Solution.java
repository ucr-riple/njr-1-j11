package jswang.MinimumWindowSubstring;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by willwjs on 10/27/15.
 * 用hash来使用two pointer方法,每次先移动右侧,若达不到要求,再移动左侧
 * http://blog.csdn.net/linhuanmars/article/details/20343903
 */
public class Solution {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) return "";
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < t.length(); ++i) {
            if (map.containsKey(t.charAt(i))) {
                map.put(t.charAt(i), map.get(t.charAt(i)) + 1);
            } else {
                map.put(t.charAt(i), 1);
            }
        }
        Map<Character, Integer> curmap = new HashMap<>();
        int left = 0, count = 0, smallStart = 0, smallLength = s.length() + 1;
        for (int right = 0; right < s.length(); ++right) {
            char r = s.charAt(right);
            if (map.containsKey(r)) {
                if (curmap.containsKey(r)) {
                    curmap.put(r, curmap.get(r) + 1);
                } else {
                    curmap.put(r, 1);
                }
                if (curmap.get(r) <= map.get(r)) {
                    count++;
                }
                while (count == t.length()) {
                    int len = right - left + 1;
                    if (len < smallLength) {
                        smallLength = len;
                        smallStart = left;
                    }
                    char l = s.charAt(left);
                    if (map.containsKey(l)) {
                        if (curmap.get(l) <= map.get(l)) {
                            count--;
                        }
                        curmap.put(l, curmap.get(l) - 1);
                    }
                    left++;
                }
            }
        }
        if (smallLength > s.length()) return "";
        return s.substring(smallStart, smallStart + smallLength);
    }

    public static void main(String[] args) {
        System.out.println(new Solution().minWindow("ADOBECODEBANC", "ABC"));
    }
}