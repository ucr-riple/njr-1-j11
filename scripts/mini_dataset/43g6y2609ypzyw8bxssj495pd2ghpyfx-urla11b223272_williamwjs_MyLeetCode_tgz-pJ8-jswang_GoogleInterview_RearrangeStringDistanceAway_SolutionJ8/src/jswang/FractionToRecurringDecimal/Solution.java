package jswang.FractionToRecurringDecimal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by willwjs on 5/14/15.
 * 1.Note that the reason why we use Long is to consider Int.MIN / (-1) => Overflow
 * 2.If the remainder is the same as before, it means there will be a loop
 */
public class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        String res = "";
        if ((numerator > 0 && denominator < 0) || (numerator < 0 && denominator > 0))
            res += '-';
        long numer = numerator;
        long denom = denominator;
        numer = Math.abs(numer);
        denom = Math.abs(denom);
        res += numer / denom; //整数部分
        long remainder = numer % denom;
        if (remainder == 0)
            return res;
        res += '.';
        Map<Long, Integer> map = new HashMap<Long, Integer>();
        while (remainder != 0) { //小数部分,模仿定义=>余数重复的时候,上一次余数出现的index就是重复部分的开始
            if (map.containsKey(remainder)) {
                int index = map.get(remainder);
                return res.substring(0, index) + '(' + res.substring(index, res.length()) + ')';
            }
            map.put(remainder, res.length());
            remainder *= 10;
            res += (remainder / denom);
            remainder %= denom;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().fractionToDecimal(1, 13));
    }
}