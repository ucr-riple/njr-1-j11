package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

public class P007 implements Problem {

    private static final int DEFAULT_DESIRED_PRIME_NUMBER_INDEX = 10001;
    private int desiredPrimeNumberIndex;

    public P007() {
        this.setDesiredPrimeNumberIndex(DEFAULT_DESIRED_PRIME_NUMBER_INDEX);
    }

    public int getDesiredPrimeNumberIndex() {
        return desiredPrimeNumberIndex;
    }

    public void setDesiredPrimeNumberIndex(int desiredPrimeNumberIndex) {
        this.desiredPrimeNumberIndex = desiredPrimeNumberIndex;
    }

    @Override
    public String getDescription() {
        return "By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, "
                + " we can see that the 6th prime is 13.\n\n"
                + "What is the 10 001st prime number?";
    }

    @Override
    public String run() {

        long largestPrime = Common.getPrimes().get(this.getDesiredPrimeNumberIndex() - 1);

        return String.valueOf(largestPrime);
    }
}
