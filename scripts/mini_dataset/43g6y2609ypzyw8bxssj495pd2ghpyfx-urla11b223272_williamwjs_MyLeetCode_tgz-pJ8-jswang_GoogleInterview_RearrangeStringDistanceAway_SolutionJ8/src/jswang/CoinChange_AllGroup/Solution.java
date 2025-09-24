package jswang.CoinChange_AllGroup;

/**
 * Created by JiashuoWang on 10/11/14.
 */
public class Solution {

    int coinGroupRC (int[] s, int m, int n) {
        if (n == 0)
            return 1;
        if (n < 0)
            return 0;
        if (m < 0)
            return 0;
        return coinGroupRC(s, m - 1, n) + coinGroupRC(s, m, n - s[m]);
    }

    int coinGroupDP (int[] s, int n) {
        int[][] state = new int[n + 1][s.length];
        for (int i = 0; i < s.length; i++)
            state[0][i] = 1;

        for (int j = 0; j < s.length; j++) {
            for (int i = 1; i < n + 1; i++) {
                state[i][j] = (i >= s[j]) ? state[i - s[j]][j] : 0; //含s[j]的情况
                state[i][j] += (j >= 1) ? state[i][j - 1] : 0; //不含s[j]的情况
            }
        }

        return state[n][s.length - 1];
    }

    int coinGroupDPS (int[] s, int n) {
        int[] state = new int[n + 1];
        state[0] = 1;

        for(int i = 0; i < s.length; i++)
            for(int j = s[i]; j <= n; j++)
                state[j] += state[j - s[i]];

        return state[n];
    }

    /*
    * Wrong Answer:
    * 3: {3}, {2, 1}, {1, 1, 1}
    * 4: {3, 1}, {2, 1, 1}, {1, 1, 1, 1}; {2, 1, 1}, {1, 1, 2}, {2, 2}, {2, 2}
    */
    /*int[] state = new int[n + 1];

    for (int m : s) {
        state[m]++;
    }

    for (int i = 2; i <= n; i++) {
        for (int j = 1; j <= i / 2; j++) {
            state[i] += state[j] * state[i - j];
        }
    }
    System.out.println(state[n]);*/

    public static void main (String[] args) {
        Solution sol = new Solution();
        int[] s = {2, 5, 3, 6};
        int n = 10;
        System.out.println("Recursive: " + sol.coinGroupRC(s, s.length - 1, n));
        System.out.println("Dynamic Programming: " + sol.coinGroupDP(s, n));
        System.out.println("Simplified Dynamic Programming: " + sol.coinGroupDPS(s, n));
    }
}
