package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Problem;

public class P010 implements Problem {

    private static final int DEFAULT_MAX_PRIME = 2000000;
    private int maxPrime;

    public P010() {
        this.setMaxPrime(DEFAULT_MAX_PRIME);
    }

    public int getMaxPrime() {
        return maxPrime;
    }

    public void setMaxPrime(int maxPrime) {
        this.maxPrime = maxPrime;
    }

    @Override
    public String getDescription() {
        return "The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.\n\n"
                + "Find the sum of all the primes below two million.";
    }

    @Override
    public String run() {
        return String.valueOf(this.sumPrimesUpTo(this.getMaxPrime()));
    }

    private long sumPrimesUpTo(int end) {
        boolean[] values = new boolean[end];

        long sum = 0;

        for (int i = 2; i < end; i++) {
            if (!values[i]) {
                sum += i;
                for (int j = i; j < end; j += i) {
                    values[j] = true;
                }
            }
        }
        return sum;
    }
}
