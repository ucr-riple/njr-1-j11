package jswang.GoogleInterview.ShortestPalindromeAddFromBack;

/**
 * Created by willwjs on 9/25/15.
 * Given a string S, return minimum number of chars that you can add to its back to obtain a palindrome. Explain the complexity
 * http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=140501&ctid=215
 * 8.29 Phone
 */
public class Solution {
    public String getShortestPalindrome(String s) {
        String rev_s = new StringBuilder(s).reverse().toString();
        String l = rev_s + "#" + s;

        int[] p = new int[l.length()];
        for (int i = 1; i < l.length(); i++) {
            int j = p[i - 1];
            while (j > 0 && l.charAt(i) != l.charAt(j))
                j = p[j - 1];
            p[i] = (l.charAt(i) == l.charAt(j) ? j + 1 : j);
        }

        return s + new StringBuilder(s.substring(0, s.length() - p[l.length() - 1])).reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(new Solution().getShortestPalindrome("bccbd"));
    }
}
