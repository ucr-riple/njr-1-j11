package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

import java.math.BigInteger;

public class P020 implements Problem {

    private static final int DEFAULT_N = 100;

    private int n;

    public P020() {
        this.setN(DEFAULT_N);
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Override
    public String getDescription() {
        return "n! means n × (n − 1) × ... × 3 × 2 × 1\n\n"
                + "For example, 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800,\n"
                + "and the sum of the digits in the number 10! is 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.\n\n"
                + "Find the sum of the digits in the number 100!";
    }

    @Override
    public String run() {

        BigInteger bigInteger = new BigInteger("1");
        for (int i = this.getN(); i > 0; i--) {
            bigInteger = bigInteger.multiply(new BigInteger(String.valueOf(i)));
        }

        return String.valueOf(Common.sumDigitsInString(String.valueOf(bigInteger)));
    }
}
