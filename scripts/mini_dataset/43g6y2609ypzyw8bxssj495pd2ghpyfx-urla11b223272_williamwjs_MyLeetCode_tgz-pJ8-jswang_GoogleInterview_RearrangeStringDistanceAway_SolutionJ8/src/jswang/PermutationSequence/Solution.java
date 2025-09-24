package jswang.PermutationSequence;

import java.util.Arrays;

/**
 * Created by willwjs on 11/15/14.
 */
public class Solution {
    public String getPermutation(int n, int k) {
        String s = new String();
        --k;
        boolean[] used = new boolean[n];

        for (int i = n - 1; i > 0; --i) {
            int factor = factorial(i), tmp = k / factor, j = 0;
            while (true) {
                if (used[j]) {
                    ++j;
                    continue;
                }
                else {
                    if (tmp == 0) break;
                    --tmp;
                    ++j;
                }
            }
            used[j] = true;
            s += j + 1;
            k %= factor;
        }

        for (int i = 0; i < n; ++i)
            if (!used[i]) {
                s += i + 1;
                break;
            }

        return s;

        /*//会超时
        int[] num = new int[n];
        for (int i = 0; i < n; i++)
            num[i] = i + 1;
        for (int i = 0; i < k - 1; i++)
            nextPermutation(num);
        String s = new String();
        for (int i : num)
            s += i;
        return s;*/
    }

    private int factorial (int n) {
        if (n == 1 || n == 0) return 1;
        return n * factorial(n - 1);
    }

    private void nextPermutation(int[] num) {
        if (num.length == 1) return;
        int i = num.length - 2;
        for (; i >= 0; i--) {
            if (num[i] < num[i + 1])
                break;
            if (i == 0) {
                Arrays.sort(num);
                return;
            }
        }
        int closebig = num[i] + 1000000, indexbig = i;
        for (int j = i + 1; j < num.length; j++)
            if (num[j] < closebig && num[j] > num[i]) {
                closebig = num[j];
                indexbig = j;
            }
        int tmp = num[indexbig];
        num[indexbig] = num[i];
        num[i] = tmp;
        Arrays.sort(num, i + 1, num.length);
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.getPermutation(3, 1));
    }
}
