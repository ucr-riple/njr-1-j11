package com.rmd.personal.projecteuler;

public class P047 implements Problem {

    private static final int DEFAULT_DISTINCT_PRIME_FACTOR_COUNT = 4;

    private int distinctPrimeFactorCount;

    public P047() {
        this.setDistinctPrimeFactorCount(DEFAULT_DISTINCT_PRIME_FACTOR_COUNT);
    }

    public int getDistinctPrimeFactorCount() {
        return distinctPrimeFactorCount;
    }

    public void setDistinctPrimeFactorCount(int distinctPrimeFactorCount) {
        this.distinctPrimeFactorCount = distinctPrimeFactorCount;
    }

    @Override
    public String getDescription() {
        return "The first two consecutive numbers to have two distinct prime factors are:\n\n"
                + "14 = 2 × 7\n"
                + "15 = 3 × 5\n\n"
                + "The first three consecutive numbers to have three distinct prime factors are:\n\n"
                + "644 = 2² × 7 × 23\n"
                + "645 = 3 × 5 × 43\n"
                + "646 = 2 × 17 × 19.\n\n"
                + "Find the first four consecutive integers to have four distinct prime factors. What is the first of "
                + "these numbers?";
    }

    @Override
    public String run() {
        long firstNumber = 0;

        boolean found = false;
        while (!found) {
            firstNumber++;
            for (int i = 0; i < this.getDistinctPrimeFactorCount(); i++) {
                if (!this.hasRequiredDistinctPrimeFactors(firstNumber + i)) {
                    found = false;
                    break;
                }
                found = true;
            }
        }


        return String.valueOf(firstNumber);
    }

    private boolean hasRequiredDistinctPrimeFactors(long number) {
        return Common.findPrimeFactorTreeForValue(number).size() == this.getDistinctPrimeFactorCount();
    }
}
