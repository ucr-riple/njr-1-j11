package com.rmd.personal.projecteuler.p20to39; // SUPPRESS CHECKSTYLE fileLength

import com.rmd.personal.projecteuler.Problem;

public class P030 implements Problem {

    private static final int DEFAULT_DIGITS = 5;

    private int digits;

    public P030() {
        this.setDigits(DEFAULT_DIGITS);
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    @Override
    public String getDescription() {
        return "Surprisingly there are only three numbers that can be written "
                + "as the sum of fourth powers of their digits:\n\n"
                + "1634 = 14 + 64 + 34 + 44\n"
                + "8208 = 84 + 24 + 04 + 84\n"
                + "9474 = 94 + 44 + 74 + 44\n"
                + "As 1 = 14 is not a sum it is not included.\n\n"
                + "The sum of these numbers is 1634 + 8208 + 9474 = 19316.\n\n"
                + "Find the sum of all the numbers that can be written as the sum of fifth powers of their digits.";
    }

    @Override
    public String run() {
        long sum = 0;

        for (long val = 0; val <= 355000; val++) {
            if (this.getDigitsToPowerOfNumDigitsSum(val) == val) {
                sum += val;
            }
        }

        sum -= 1; // Remove 1^numDigits

        return String.valueOf(sum);
    }

    private long getDigitsToPowerOfNumDigitsSum(long value) {
        long sum = 0;

        while (value > 0) {
            sum += Math.pow((value % 10), this.getDigits());
            value = value / 10;
        }

        return sum;
    }
}
