package jswang.ImplementStrStr;

/**
 * Created by willwjs on 11/21/14.
 */
public class Solution {
    public int strStr(String haystack, String needle) {
        if (haystack.length() == 0 && needle.length() == 0) return 0;
        if (needle.length() == 0) return 0;
        for (int i = 0; i <= haystack.length() - needle.length(); ++i) {
            int j = 0, mark = i;
            while (haystack.charAt(mark) == needle.charAt(j) && mark < haystack.length()) {
                if (j == needle.length() - 1) return i;
                ++mark;
                ++j;
            }
        }
        return -1;
    }
}
