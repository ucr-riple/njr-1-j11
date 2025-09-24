package jswang.BestTimeToBuySellStock3;

/**
 * Created by JiashuoWang on 10/14/14.
 */
public class Solution {
    int maxProfit (int[] prices) {
        if (prices.length < 2) return 0;
        int[] f = new int[prices.length], g = new int[prices.length];

        for (int i = 1, bottom = prices[0]; i < prices.length; i++) {
            if (prices[i] < bottom) bottom = prices[i];
            f[i] = (f[i - 1] > prices[i] - bottom) ? f[i - 1] : prices[i] - bottom;
        }
        for (int i = prices.length - 2, peak = prices[prices.length - 1]; i >= 0; i--) {
            if (prices[i] > peak) peak = prices[i];
            g[i] = (g[i + 1] > peak - prices[i]) ? g[i + 1] : peak - prices[i];
        }

        int profit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (f[i] + g[i] > profit) profit = f[i] + g[i];
        }

        return profit;
    }

    public static void main (String[] args) {
        int[] prices = {1, -2, 4, 2};
        Solution sol = new Solution();
        System.out.println(sol.maxProfit(prices));
    }
}

/*错误答案！因为要求买之前必须先卖出，这里由于只有一个minbuy，对于1，2，4的情况，会得到4的结果，而正确答案是3
public class Solution {
    int maxProfit (int[] prices) {
        if (prices.length < 2) return 0;
        int maxmax = 0, max = 0, temp = 0, minbuy = prices[0];

        for (int i = 1; i < prices.length; i++) {
            temp = prices[i] - minbuy;
            if (temp > maxmax){
                max = maxmax;
                maxmax = temp;
            }
            else if (temp > max) {
                max = temp;
            }
            minbuy = (minbuy < prices[i]) ? minbuy : prices[i];
        }

        if (max < 0) return maxmax;
        else return maxmax + max;
    }
}*/
