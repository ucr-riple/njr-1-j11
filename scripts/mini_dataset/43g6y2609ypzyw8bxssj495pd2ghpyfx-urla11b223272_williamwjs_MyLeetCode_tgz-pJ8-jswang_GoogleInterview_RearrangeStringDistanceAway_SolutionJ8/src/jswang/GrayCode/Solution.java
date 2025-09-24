package jswang.GrayCode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by willwjs on 11/17/14.
 */
public class Solution {
    public List<Integer> grayCode(int n) {
        List<Integer> list = new LinkedList<Integer>();
        int num = 1;
        for (int i = 1; i <= n; ++i)
            num *= 2;

        for (int i = 0; i < num; ++i) {
            int tmp = i, result = 0;
            result += (tmp >> (n - 1)) << (n - 1);
            for (int digit = n; digit > 1; --digit) {
                if ((((tmp >> (digit - 1)) & 1) ^ ((tmp >> (digit - 2)) & 1)) == 1)
                    result += 1 << (digit - 2);
            }
            list.add(result);
        }
        return list;
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        List<Integer> list = sol.grayCode(3);
        for (int i : list)
            System.out.print(i + " ");
        System.out.println();
    }
}
