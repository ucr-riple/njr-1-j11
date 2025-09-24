package jswang.Max2DContSubarray;

/**
 * Created by JiashuoWang on 10/5/14.
 */
public class Solution {
    void mc2DSub (int[][] s) {
        int row = s.length;
        int col = s[0].length;
        int max = 0;
        int[] sum = new int[col];

        for (int i = 0; i < row; i++) { //压缩，将每行分别加到第一行；再分别加第二行
            for (int j = i; j < row; j++) {
                if (j != i)
                    for (int k = 0; k < col; k++)
                        s[i][k] += s[j][k];
                sum[0] = s[i][0];
                for (int k = 1; k < col; k++) {
                    sum[k] = (sum[k - 1] + s[i][k] > s[i][k]) ? s[i][k - 1] + s[i][k] : s[i][k];
                    if (max < sum[k]) max = sum[k];
                }
            }
        }
        System.out.println(max);
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        int[][] s = {{0,-2,-7,0},{9,2,-6,2},{-4,1,-4,1},{-1,8,0,-2}};
        sol.mc2DSub(s);
    }
}
