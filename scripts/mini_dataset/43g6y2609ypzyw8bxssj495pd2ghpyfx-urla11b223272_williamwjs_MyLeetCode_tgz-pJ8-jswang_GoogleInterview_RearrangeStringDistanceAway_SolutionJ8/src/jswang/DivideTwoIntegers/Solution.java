package jswang.DivideTwoIntegers;

/**
 * Created by willwjs on 2/22/15.
 */
public class Solution {
    public int divide(int dividend, int divisor) {
        if (divisor == 0)
            return Integer.MAX_VALUE;
        boolean isNeg = (dividend ^ divisor) >>> 31 == 1;
        int res = 0;
        if (dividend == Integer.MIN_VALUE)
        {
            dividend += Math.abs(divisor);
            if (divisor == -1)
                return Integer.MAX_VALUE;
            res++;
        }
        if (divisor == Integer.MIN_VALUE)
            return res;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        int digit = 0;
        while (divisor <= (dividend >> 1))
        {
            divisor <<= 1;
            digit++;
        }
        while (digit >= 0)
        {
            if (dividend >= divisor)
            {
                res += 1 << digit;
                dividend -= divisor;
            }
            divisor >>= 1;
            digit--;
        }
        return isNeg ? -res : res;
    }

    public int divide1(int dividend, int divisor) { //O(N)
        if (divisor == 0 && dividend > 0)
            return Integer.MAX_VALUE;
        else if (divisor == 0 && dividend < 0)
            return Integer.MIN_VALUE;
        else if (divisor == 0 && dividend == 0)
            return 0;

        boolean positive = false;
        if (dividend > 0 && divisor > 0) {
            dividend = 0 - dividend;
            divisor = 0 - divisor;
            positive = true;
        }
        else if (dividend < 0 && divisor < 0)
            positive = true;
        if (dividend > 0)
            dividend = 0 - dividend;
        if (divisor > 0)
            divisor = 0 - divisor;

        int quotient = 0;
        for (; dividend < 0; dividend -= divisor)
            --quotient;

        if (positive && quotient != Integer.MIN_VALUE)
            return  0 - quotient;
        else if (positive && quotient == Integer.MIN_VALUE)
            return Integer.MAX_VALUE;
        else
            return quotient;
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.divide(Integer.MAX_VALUE, 1));
    }
}