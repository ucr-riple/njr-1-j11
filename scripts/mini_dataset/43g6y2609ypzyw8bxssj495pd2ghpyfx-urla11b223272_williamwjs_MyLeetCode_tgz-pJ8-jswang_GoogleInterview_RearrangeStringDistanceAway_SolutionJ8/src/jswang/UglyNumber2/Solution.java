package jswang.UglyNumber2;

/**
 * Created by willwjs on 8/31/15.
 */
public class Solution {
    public int nthUglyNumber(int n) {
        int res[] = new int[n];
        res[0] = 1;
        int two_p = 0;
        int three_p = 0;
        int five_p = 0;
        for (int i = 1; i < n; ++i) {
            int two_v = res[two_p] * 2;
            int three_v = res[three_p] * 3;
            int five_v = res[five_p] * 5;
            int tmp_s = two_v < three_v ? two_v : three_v;
            if (tmp_s > five_v) tmp_s = five_v;
            res[i] = tmp_s;
            while (tmp_s >= res[two_p] * 2) two_p++;
            while (tmp_s >= res[three_p] * 3) three_p++;
            while (tmp_s >= res[five_p] * 5) five_p++;
        }
        return res[n - 1];
    }

    public static void main(String[] args) {
        System.out.println(new Solution().nthUglyNumber(2));
    }
}