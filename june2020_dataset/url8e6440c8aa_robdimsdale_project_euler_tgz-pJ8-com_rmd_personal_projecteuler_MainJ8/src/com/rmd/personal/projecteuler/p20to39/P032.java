package com.rmd.personal.projecteuler.p20to39;

import java.util.HashSet;
import java.util.Set;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

public class P032 implements Problem {

    private static final int DEFAULT_START = 1;
    private static final int DEFAULT_END = 9876;

    private static final long SMALLEST_NINE_DIGIT_NUMBER = 100000000L;

    private int start;
    private int end;

    public P032() {
        this.setStart(DEFAULT_START);
        this.setEnd(DEFAULT_END);
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String getDescription() {
        return "We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n "
                + "exactly once; for example, the 5-digit number, 15234, is 1 through 5 pandigital.\n\n"
                + "The product 7254 is unusual, as the identity, 39 × 186 = 7254, containing multiplicand, "
                + "multiplier, and product is 1 through 9 pandigital.\n\n"
                + "Find the sum of all products whose multiplicand/multiplier/product identity can be "
                + "written as a 1 through 9 pandigital.\n\n"
                + "HINT: Some products can be obtained in more than one way so be sure to only include "
                + "it once in your sum.";
    }

    @Override
    public String run() {
        Set<Long> products = new HashSet<Long>();

        for (long i = this.getStart(); i <= this.getEnd(); i++) {
            for (long j = i; j <= this.getEnd(); j++) {
                long product = i * j;
                if (product <= DEFAULT_END) {
                    long concatenated = this.concatenateProductMultiplicandMultiplier(product, i, j);
                    if (concatenated < SMALLEST_NINE_DIGIT_NUMBER) {
                        continue;
                    }
                    final int maxDigit = 9;
                    if (Common.isPandigital(concatenated, maxDigit)) {
                        products.add(product);
                    }
                }
            }
        }

        long sum = 0;
        for (long product : products) {
            sum += product;
        }

        return String.valueOf(sum);
    }

    private long concatenateProductMultiplicandMultiplier(long product, long multiplicand, long multiplier) {
        long concatenated = Common.concatenateNumbers(product, multiplicand);
        concatenated = Common.concatenateNumbers(concatenated, multiplier);
        return concatenated;
    }
}
