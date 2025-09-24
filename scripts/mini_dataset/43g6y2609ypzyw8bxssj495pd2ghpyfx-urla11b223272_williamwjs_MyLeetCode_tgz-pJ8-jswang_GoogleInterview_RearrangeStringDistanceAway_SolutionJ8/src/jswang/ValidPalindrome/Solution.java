package jswang.ValidPalindrome;

/**
 * Created by willwjs on 11/21/14.
 * 注意使用Character.isLetterOrDigit和Character.toLowerCase方法
 */
public class Solution {
    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i))) i++;
            while (i < j && !Character.isLetterOrDigit(s.charAt(j))) j--;
            if (Character.toLowerCase(s.charAt(i))
                    != Character.toLowerCase(s.charAt(j))) {
                return false;
            }
            i++; j--;
        }
        return true;

        /*
        if (s.length() == 0) return true;
        String tmp = s.toLowerCase();
        for (int i = 0, j = s.length() - 1; i < j; ++i, --j) {
            while (!((tmp.charAt(i) >= 'a' && tmp.charAt(i) <= 'z')
                    || (tmp.charAt(i) >= '0' && tmp.charAt(i) <= '9'))) {
                ++i;
                if (i >= j) return true;
            }
            while (!((tmp.charAt(j) >= 'a' && tmp.charAt(j) <= 'z')
                    || (tmp.charAt(j) >= '0' && tmp.charAt(j) <= '9'))) {
                --j;
                if (i >= j) return true;
            }
            if (tmp.charAt(i) != tmp.charAt(j)) return false;
        }
        return true;*/
    }
}
