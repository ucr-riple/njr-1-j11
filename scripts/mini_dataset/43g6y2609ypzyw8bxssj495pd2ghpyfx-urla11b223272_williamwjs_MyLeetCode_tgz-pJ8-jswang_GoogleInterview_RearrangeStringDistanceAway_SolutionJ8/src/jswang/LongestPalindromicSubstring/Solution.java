package jswang.LongestPalindromicSubstring;

/**
 * Created by willwjs on 1/13/15.
 * 需要考虑最长回文长度是奇数和偶数的情况，为了简化操作，采用插入n+1个空格的方式
 * 但是实际并没有添加（否则会超时），在虚拟使用的时候注意奇偶处理
 */
public class Solution {
    public String longestPalindrome(String s) {
        int start = 0, max = 1;
        for (int i = 1; i < 2 * s.length(); ++i) {
            int tmpmax = 1, j = 1;
            while (i - j >= 0 && i + j < 2 * s.length() + 1) {
                if ((i - j) % 2 == 0) {
                    ++j;
                    tmpmax += 2;
                }
                else {
                    if (s.charAt((i - j) / 2) != s.charAt((i + j) / 2)) {
                        if (tmpmax > max) {
                            max = tmpmax;
                            start = i - j + 1;
                        }
                        break;
                    }
                    ++j;
                    tmpmax += 2;
                }
            }
            if (i - j < 0 || i + j >= 2 * s.length() + 1) {
                if (tmpmax > max) {
                    max = tmpmax;
                    start = i - j + 1;
                }
            }
        }
        start = (start + 1) / 2;
        max /= 2;
        return s.substring(start, start + max);
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.longestPalindrome("acbbd"));
    }
}