package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Problem;

public class P014 implements Problem {

    private static final long DEFAULT_START_VALUE = 1;
    private static final long DEFAULT_END_VALUE = 1000000;

    private long startVal;
    private long endVal;

    private int[] chainLengths;

    public P014() {
        this.setStartVal(DEFAULT_START_VALUE);
        this.setEndVal(DEFAULT_END_VALUE);
    }

    public long getStartVal() {
        return startVal;
    }

    public void setStartVal(long startVal) {
        this.startVal = startVal;
    }

    public long getEndVal() {
        return endVal;
    }

    public void setEndVal(long endVal) {
        this.endVal = endVal;
    }

    private int[] getChainLengths() {
        return this.chainLengths;
    }

    @Override
    public String getDescription() {
        return "The following iterative sequence is defined for the set of positive integers:\n\n"
                + "n → n/2 (n is even)\n"
                + "n → 3n + 1 (n is odd)\n\n"
                + "Using the rule above and starting with 13, we generate the following sequence:\n\n"
                + "13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1\n"
                + "It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms. Although it"
                + " has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.\n\n"
                + "Which starting number, under one million, produces the longest chain?\n\n"
                + "NOTE: Once the chain starts the terms are allowed to go above one million.";
    }

    @Override
    public String run() {
        this.chainLengths = new int[(int) this.getEndVal() + 1];
        this.getChainLengths()[1] = 1; // 0 index is never used - we're 1-index based.

        int maxChainLength = 1;
        long maxChainLengthStartingVal = 1;

        for (long start = this.getStartVal(); start <= this.getEndVal(); start++) {
            int chainLength = this.getOrPopulateChainLength(start);
            if (chainLength > maxChainLength) {
                maxChainLength = chainLength;
                maxChainLengthStartingVal = start;
            }
        }
        return String.valueOf(maxChainLengthStartingVal);
    }

    private int getOrPopulateChainLength(long value) {
        if (value < this.getChainLengths().length && this.getChainLengths()[(int) value] != 0) {
            return this.getChainLengths()[(int) value];
        } else {
            int count = this.getOrPopulateChainLength(this.nextValue(value)) + 1;
            if (value < this.getChainLengths().length) {
                this.getChainLengths()[(int) value] = count;
            }
            return count;
        }
    }

    private long nextValue(long n) {
        if (n % 2 == 0) {
            return n / 2;
        } else {
            return 3 * n + 1;
        }
    }
}
