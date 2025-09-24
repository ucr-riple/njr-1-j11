package jswang.PlusOne;

/**
 * Created by willwjs on 11/17/14.
 */
public class Solution {
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; ++digits[i] > 9 && i >= 0; --i) {
            digits[i] -= 10;
            if (i == 0) {
                int[] tmp = new int[digits.length + 1];
                tmp[0] = 1;
                for (int j = 0; j < digits.length; ++j)
                    tmp[j + 1] = digits[j];
                return tmp;
            }
        }
        return digits;
    }
}
