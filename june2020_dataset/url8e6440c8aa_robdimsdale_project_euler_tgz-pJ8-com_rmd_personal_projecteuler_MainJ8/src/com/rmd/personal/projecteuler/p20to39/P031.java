package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.Problem;

import java.util.Arrays;

public class P031 implements Problem {

    private static final int DEFAULT_TOTAL = 200;
    private static final int[] DEFAULT_COINS = {1, 2, 5, 10, 20, 50, 100, 200};

    private int total;

    private int[] coins;

    public P031() {
        this.setTotal(DEFAULT_TOTAL);
        this.setCoins(DEFAULT_COINS);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private int[] getCoins() {
        return this.coins;
    }

    private void setCoins(int[] coins) {
        this.coins = coins;
        Arrays.sort(coins);
    }

    @Override
    public String getDescription() {
        return "In England the currency is made up of pound, £, and pence, p, "
                + "and there are eight coins in general circulation:\n\n"
                + "1p, 2p, 5p, 10p, 20p, 50p, £1 (100p) and £2 (200p).\n"
                + "It is possible to make £2 in the following way:\n\n"
                + "1×£1 + 1×50p + 2×20p + 1×5p + 1×2p + 3×1p\n"
                + "How many different ways can £2 be made using any number of coins?";
    }

    @Override
    public String run() {
        int[] counts = new int[this.getTotal() + 1];
        counts[0] = 1;
        for (int coin : this.getCoins()) {
            for (int i = coin; i < counts.length; i++) {
                counts[i] += counts[i - coin];
            }
        }

        return String.valueOf(counts[this.getTotal()]);
    }
}
