package leetcode;

/**
 * Created by sobercheg on 10/29/13.
 */
public class BestTimeToBuyStock {

    public static int[] getBestBuyAndSellTimes(int[] prices) {
        int[] buySell = new int[2];
        int buyTime = 0, sellTime = -1, maxDiff = -1, min = -1;

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < prices[buyTime]) {
                min = i;
            }
            int diff = prices[i] - prices[min];
            if (diff > maxDiff) {
                buyTime = min;
                sellTime = i;
                maxDiff = diff;
            }
        }

        buySell[0] = buyTime;
        buySell[1] = sellTime;
        return buySell;
    }
}
