package jswang.GoogleInterview.ShortestPalindromeAddFromFront;

/**
 * Created by willwjs on 9/25/15.
 * KMP
 */
public class Solution {
    public String shortestPalindrome(String s) {
        String rev_s = new StringBuilder(s).reverse().toString();
        String l = s + "#" + rev_s;

        int[] p = new int[l.length()];
        for (int i = 1; i < l.length(); i++) {
            int j = p[i - 1];
            while (j > 0 && l.charAt(i) != l.charAt(j))
                j = p[j - 1];
            p[i] = (l.charAt(i) == l.charAt(j) ? j + 1 : j);
        }

        return rev_s.substring(0, s.length() - p[l.length() - 1]) + s;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().shortestPalindrome("babc"));
    }
}