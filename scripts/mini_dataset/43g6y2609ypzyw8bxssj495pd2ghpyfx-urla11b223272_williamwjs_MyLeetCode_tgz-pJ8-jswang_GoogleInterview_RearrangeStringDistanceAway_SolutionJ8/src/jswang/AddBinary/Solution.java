package jswang.AddBinary;

/**
 * Created by willwjs on 11/22/14.
 */
public class Solution {
    public String addBinary(String a, String b) {
        if (a.length() == 0) return b;
        if (b.length() == 0) return a;
        String result = "";
        int ia = a.length() - 1, ib = b.length() - 1, carry = 0;
        for (; ia >= 0 && ib >= 0; --ia, --ib) {
            if (a.charAt(ia) == '1' && b.charAt(ib) == '1') {
                result = carry + result;
                carry = 1;
            }
            else if (a.charAt(ia) == '0' && b.charAt(ib) == '0') {
                result = carry + result;
                carry = 0;
            }
            else {
                if (carry == 0) {
                    result = 1 + result;
                }
                else {
                    result = 0 + result;
                }
            }
        }
        if (ia < 0) {
            for (; ib >= 0; --ib) {
                if (b.charAt(ib) == '1') {
                    if (carry == 0)
                        result = 1 + result;
                    else
                        result = 0 + result;
                }
                else {
                    if (carry == 0)
                        result = 0 + result;
                    else {
                        result = 1 + result;
                        carry = 0;
                    }
                }
            }
        }
        else if (ib < 0) {
            for (; ia >= 0; --ia) {
                if (a.charAt(ia) == '1') {
                    if (carry == 0)
                        result = 1 + result;
                    else
                        result = 0 + result;
                }
                else {
                    if (carry == 0)
                        result = 0 + result;
                    else {
                        result = 1 + result;
                        carry = 0;
                    }
                }
            }
        }
        return carry == 1 ? 1 + result : result;
    }

    public static void main (String[] args) {
        String a = "1";
        String b = "111";
        Solution sol = new Solution();
        System.out.println(sol.addBinary(a, b));
    }
}
