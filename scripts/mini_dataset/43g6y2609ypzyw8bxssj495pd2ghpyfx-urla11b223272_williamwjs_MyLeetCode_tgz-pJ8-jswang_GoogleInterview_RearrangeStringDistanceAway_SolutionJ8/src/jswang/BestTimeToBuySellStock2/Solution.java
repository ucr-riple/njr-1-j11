package jswang.BestTimeToBuySellStock2;

/**
 * Created by JiashuoWang on 10/13/14.
 */
//贪婪算法，将所有相邻值递增的profit加起来
public class Solution {
    int maxProfit (int[] prices) {
        if (prices.length < 2) return 0;

        int profit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
        }

        return profit;
    }
}