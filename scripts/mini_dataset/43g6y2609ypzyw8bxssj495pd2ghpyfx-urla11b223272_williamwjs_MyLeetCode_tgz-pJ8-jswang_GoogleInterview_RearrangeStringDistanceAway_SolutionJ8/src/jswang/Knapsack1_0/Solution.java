package jswang.Knapsack1_0;

/**
 * Created by JiashuoWang on 10/4/14.
 */
public class Solution {
    void Knapsack(int totalV, int[] v, int[] w) { //w是宝石的价值
        int[] state = new int[totalV + 1]; //书包从装了0个单位到totalV个单位的总价值
        int totalN = v.length;
        for (int i = 0; i < totalN; i++) {//背包从第0块宝石到遍历到第totalN－1块宝石
            for (int j = totalV; j >= 0; j--) {
                if (j < v[i]) continue;
                else
                    state[j] = (state[j] > state[j - v[i]] + w[i]) ? state[j] : state[j - v[i]] + w[i];
            }
        }
        System.out.println(state[totalV]);
    }

    public static void main (String[] args) {
        Solution s = new Solution();
        int totalV = 10;
        int[] v = {4, 3, 5, 2, 5};
        int[] w = {9, 6, 1, 4, 1};
        s.Knapsack(totalV, v, w);
    }
}