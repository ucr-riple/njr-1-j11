package careercup.google;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sobercheg on 12/16/13.
 * <p/>
 * http://www.careercup.com/question?id=5083698865111040
 * Given a amount and several denominations of coins, find all possible ways that amount can be formed? eg amount = 5, denominations = 1,2,3.
 * Ans- 5 ways
 * 1) 1,1,1,1,1
 * 2) 1,1,1,2 (combinations aren't counted eg 1,2,1,1 etc)
 * 3) 1,1,3
 * 4) 1,2,2
 * 5) 2,3
 */

class MutableInt {
    public MutableInt(int i) {
        this.i = i;
    }

    int i;
}

public class CoinDenominations {
    public int getNumOfWays(int amount, List<Integer> denoms) {
       /* MutableInt ways = new MutableInt(0);
        getNumOfWays(amount, denoms, 0, 0, ways);
        return ways.i;
        */
        return getNumOfWays(amount, denoms, 0);
    }

    public void getNumOfWays(int amount, List<Integer> denoms, int currentIndex, int currentAmount, MutableInt numOfWays) {

        // base cases
        if (currentIndex == denoms.size()) return;
        if (currentAmount > amount) return;
        if (currentAmount == amount) {
            numOfWays.i++;
            return;
        }

        // recursive case
        for (int i = currentIndex; i < denoms.size(); i++) {
            getNumOfWays(amount, denoms, i, currentAmount + denoms.get(i), numOfWays);
        }

    }

    public int getNumOfWays(int amount, List<Integer> denoms, int currentIndex) {
        int result = 0;
        if (amount == 0) {
            return 1;
        }

        for (int i = currentIndex; i < denoms.size(); i++) {
            if (amount >= denoms.get(i)) {
                result += getNumOfWays(amount - denoms.get(i), denoms, i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        CoinDenominations coinDenominations = new CoinDenominations();
        System.out.println(coinDenominations.getNumOfWays(6, Arrays.asList(1, 2, 3, 4)));
    }
}
