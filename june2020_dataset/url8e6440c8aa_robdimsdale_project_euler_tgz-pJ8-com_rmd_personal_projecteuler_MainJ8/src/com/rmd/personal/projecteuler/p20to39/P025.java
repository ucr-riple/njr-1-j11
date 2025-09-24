package com.rmd.personal.projecteuler.p20to39; // SUPPRESS CHECKSTYLE fileLength

import com.rmd.personal.projecteuler.Problem;

import java.math.BigInteger;

public class P025 implements Problem {

    private static final int DEFAULT_NUMBER_OF_DIGITS = 1000;

    private int numberOfDigits;

    public P025() {
        this.setNumberOfDigits(DEFAULT_NUMBER_OF_DIGITS);
    }

    public int getNumberOfDigits() {
        return numberOfDigits;
    }

    public void setNumberOfDigits(int numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    @Override
    public String getDescription() {
        return "The Fibonacci sequence is defined by the recurrence relation:\n\n"
                + "Fn = Fn−1 + Fn−2, where F1 = 1 and F2 = 1.\n"
                + "Hence the first 12 terms will be:\n\n"
                + "F1 = 1\n"
                + "F2 = 1\n"
                + "F3 = 2\n"
                + "F4 = 3\n"
                + "F5 = 5\n"
                + "F6 = 8\n"
                + "F7 = 13\n"
                + "F8 = 21\n"
                + "F9 = 34\n"
                + "F10 = 55\n"
                + "F11 = 89\n"
                + "F12 = 144\n"
                + "The 12th term, F12, is the first term to contain three digits.\n\n"
                + "What is the first term in the Fibonacci sequence to contain 1000 digits?";
    }

    @Override
    public String run() {

        BigInteger f2;
        BigInteger f1 = new BigInteger("1");
        BigInteger fCurrent = new BigInteger("2");
        int currentDigitCount = 0;
        int currentIndex = 2; //zeroth-indexed
        while (currentDigitCount < this.getNumberOfDigits()) {
            f2 = f1;
            f1 = fCurrent;
            fCurrent = f2.add(f1);
            currentDigitCount = fCurrent.toString().length();
            currentIndex++;
        }

        return String.valueOf(currentIndex + 1);
    }
}
