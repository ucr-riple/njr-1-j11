package jswang.NextPermutation;

import java.util.Arrays;

/**
 * Created by willwjs on 11/15/14.
 * 注意：Arrays.sort()是左闭右开区间
 */
public class Solution {
    public void nextPermutation(int[] num) {
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
        Arrays.sort(num, i + 1, num.length); //不要排序，直接翻转就行
    }

    public static void main (String[] args) {
        int[] num = {1, 3, 2};
        Solution sol = new Solution();
        sol.nextPermutation(num);
        for (int i : num)
            System.out.print(i + " ");
        System.out.println();
    }
}
