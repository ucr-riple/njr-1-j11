package jswang.GoogleInterview.StockEveryOtherDay;

/**
 * Created by willwjs on 9/17/15.
 * Stock + House robber 结合题 可以无限买卖，但卖了之后要至少隔一天才能买
 * http://www.1point3acres.com/bbs/thread-140228-1-1.html
 * 8.26 onsite
 */
public class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        int[] hold = new int[prices.length];
        int[] unhold = new int[prices.length];
        hold[0] = -prices[0];
        unhold[0] = 0;
        for (int i = 1; i < prices.length; ++i) {
            hold[i] = Math.max(hold[i - 1], (i > 1 ? unhold[i - 2] - prices[i] : -prices[i]));
            unhold[i] = Math.max(hold[i - 1] + prices[i], unhold[i - 1]);
        }
        return unhold[prices.length - 1];
    }

    public static void main(String[] args) {
        System.out.println(new Solution().maxProfit(new int[] {4,3,2,1}));
    }
}
