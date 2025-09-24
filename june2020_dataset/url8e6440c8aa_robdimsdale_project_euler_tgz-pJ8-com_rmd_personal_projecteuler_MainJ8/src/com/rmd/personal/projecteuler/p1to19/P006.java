package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

public class P006 implements Problem {

    private static final int DEFAULT_MAX_NUMBER = 100;
    private int maxNumber;

    public P006() {
        this.setMaxNumber(DEFAULT_MAX_NUMBER);
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    @Override
    public String getDescription() {
        return "The sum of the squares of the first ten natural numbers is,\n\n"
                + "1^2 + 2^2 + ... + 10^2 = 385\n"
                + "The square of the sum of the first ten natural numbers is,\n\n"
                + "(1 + 2 + ... + 10)^2 = 55^2 = 3025\n"
                + "Hence the difference between the sum of the squares of the first ten natural numbers and the square "
                + "of the sum is 3025 − 385 = 2640.\n\n"
                + "Find the difference between the sum of the squares of the first one hundred natural numbers "
                + "and the square of the sum.";
    }

    @Override
    public String run() {

        long sumOfSquares = 0;
        for (long i = 1L; i <= this.getMaxNumber(); i++) {
            sumOfSquares += (long) Math.pow(i, 2);
        }

        long squareOfSum = (long) Math.pow(Common.sum(this.getMaxNumber()), 2);

        return String.valueOf(squareOfSum - sumOfSquares);
    }
}
