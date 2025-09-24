package jswang.BestTimeToBuySellStock1;

/**
 * Created by JiashuoWang on 10/13/14.
 */
//贪婪算法
public class Solution {
    int maxProfit (int[] prices,int money) {
        if (prices.length < 2) return 0;

        int minbuy = prices[0];
        int profit = 0;
        boolean flag;

        for (int i = 1; i < prices.length; i++) {
            flag = (profit > prices[i] - minbuy);
            profit = (profit > prices[i] - minbuy) ? profit : prices[i] - minbuy;
            minbuy = (minbuy < prices[i]) ? minbuy : ((flag)? minbuy : prices[i]);
        }

        return profit * (money / minbuy) + money;
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        System.out.print(sol.maxProfit(new int[]{5, 10, 1, 5}, 5));
    }
}

class Solution4 {
    int maxProfit(int[] prices) {
        if (prices.length < 2) return 0;

        int minbuy = prices[0];
        int profit = 0;

        for (int i = 1; i < prices.length; i++) {
            profit = (profit > prices[i] - minbuy) ? profit : prices[i] - minbuy;
            minbuy = (minbuy < prices[i]) ? minbuy : prices[i];
        }

        return profit;
    }
}

class Solution3 {
    public int maxProfit(int[] prices) {
        if( prices==null || prices.length<2 ) return 0;
        int local_max = 0;
        int global_max = 0;
        for(int i = 1; i < prices.length; ++i) {
            local_max = Math.max(prices[i] - prices[i-1] + local_max, 0);
            global_max= Math.max(global_max, local_max);
        }
        return global_max;
    }
}

//DP,首先作差分，然后转化为最大连续子序列和
class Solution2 {
    int maxProfit (int[] price) {
        if (price.length < 2) return 0;

        for (int i = price.length - 1; i > 0; i--) {
            price[i] -= price[i - 1];
        }

        int[] state = new int[price.length];
        int max = 0;

        for (int i = 1; i < price.length; i++) {
            state[i] = (price[i] > price[i] + state[i - 1]) ? price[i] : price[i] + state[i - 1];
            max = (max > state[i]) ? max : state[i];
        }

        return max;
    }
}
