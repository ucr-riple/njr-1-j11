package com.rmd.personal.projecteuler;

public class P041 implements Problem {

    private static final long DEFAULT_MAX = 987654321L;

    private long max;

    public P041() {
        this.setMax(DEFAULT_MAX);
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    @Override
    public String getDescription() {
        return "We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly "
                + "once. For example, 2143 is a 4-digit pandigital and is also prime.\n\n"
                + "What is the largest n-digit pandigital prime that exists?";
    }

    @Override
    public String run() {
        long largest = 0;

        for (long prime : Common.getPrimes()) {
            if (prime > this.getMax()) {
                break;
            }
            if (Common.isPandigital(prime, Common.findMaxDigit(prime))) {
                largest = prime; // Don't need to test as we know the prime list is ordered.
            }
        }

        return String.valueOf(largest);
    }
}
