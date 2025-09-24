package jswang.BitwiseANDofNumbersRange;

/**
 * Created by willwjs on 5/11/15.
 * Be careful about m is equal to n, which could result in dead loop
 * Because in normal case we want the loop end at Line 15.
 */
public class Solution {
    public int rangeBitwiseAnd(int m, int n) {
        if (m == n) return m;
        int res = 0;
        while (m >= 0) {
            int mlevel = (int) (Math.log10(m)/Math.log10(2));
            int nlevel = (int) (Math.log10(n)/Math.log10(2));
            if (mlevel != nlevel)
                return res;
            int tmp = (int) Math.pow(2, mlevel);
            res += tmp;
            m -= tmp;
            n -= tmp;
        }
        return res;
    }

    public static void main (String[] args) {
        System.out.println(new Solution().rangeBitwiseAnd(6, 7));
    }
}