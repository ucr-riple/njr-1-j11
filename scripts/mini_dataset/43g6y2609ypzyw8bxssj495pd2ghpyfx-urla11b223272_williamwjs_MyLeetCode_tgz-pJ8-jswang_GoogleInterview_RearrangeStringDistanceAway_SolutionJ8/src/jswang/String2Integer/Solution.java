package jswang.String2Integer;

/**
 * Created by willwjs on 11/21/14.
 */
public class Solution {
    public int atoi(String str) {
        int begin = 0;
        boolean positive = true;
        for (; begin < str.length() && str.charAt(begin) == ' '; ++begin);
        if (begin == str.length()) return 0;
        if (str.charAt(begin) == '+') {
            ++begin;
        }
        else if (str.charAt(begin) == '-') {
            ++begin;
            positive = false;
        }
        else if (str.charAt(begin) >= '0' && str.charAt(begin) <= '9') {}
        else
            return 0;

        int end = begin, result = 0;
        while (end < str.length() && str.charAt(end) >= '0' && str.charAt(end) <= '9')
            ++end;
        if (end - begin > 10) return positive ? 2147483647 : -2147483648;
        if (end - begin == 10) {
            if (positive && str.substring(begin, end).compareTo("2147483647") >= 0)
                return 2147483647;
            if (!positive && str.substring(begin, end).compareTo("2147483648") >= 0)
                return -2147483648;
        }
        for (; begin < end; ++begin) {
            result += (str.charAt(begin) - '0') * Math.pow(10, end - begin - 1);
        }
        return positive ? result : -result;

        /*//Some guy's answer
        int n = str.length();
        int i = 0;
        while (i < n && Character.isWhitespace(str.charAt(i))) i++;
        int sign = 1;
        if (i < n && str.charAt(i) == '+') {
            i++;
        } else if (i < n && str.charAt(i) == '-') {
            sign = -1;
            i++;
        }
        int num = 0;
        while (i < n && Character.isDigit(str.charAt(i))) {
            int digit = Character.getNumericValue(str.charAt(i));
            if (num > maxDiv10 || num == maxDiv10 && digit >= 8) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            num = num * 10 + digit;
            i++;
        }
        return sign * num;*/
    }

    public static void main (String[] args) {
        String s = "   -45877777777782asd78787878";
        Solution sol = new Solution();
        System.out.println(sol.atoi(s));
    }
}
