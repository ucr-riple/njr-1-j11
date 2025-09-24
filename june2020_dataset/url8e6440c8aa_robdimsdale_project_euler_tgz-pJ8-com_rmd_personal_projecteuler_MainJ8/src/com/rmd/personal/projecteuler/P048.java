package com.rmd.personal.projecteuler;

public class P048 implements Problem {

    private static final int DEFAULT_N = 1000;
    private static final int DEFAULT_NUMBER_OF_DIGITS = 10;

    private int n;
    private int numberOfDigits;

    public P048() {
        this.setN(DEFAULT_N);
        this.setNumberOfDigits(DEFAULT_NUMBER_OF_DIGITS);
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getNumberOfDigits() {
        return numberOfDigits;
    }

    public void setNumberOfDigits(int numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    @Override
    public String getDescription() {
        return "The series, 11 + 22 + 33 + ... + 1010 = 10405071317.\n\n"
                + "Find the last ten digits of the series, 11 + 22 + 33 + ... + 10001000.";
    }

    @Override
    public String run() {
        long sum = 0;

        final long inverseDigitMask = (long) Math.pow(10, this.getNumberOfDigits());
        for (int i = 1; i <= this.getN(); i++) {
            long currentSum = i;
            for (int j = 1; j < i; j++) {
                currentSum *= i;
                currentSum %= inverseDigitMask;
            }
            sum += currentSum;
            sum %= inverseDigitMask;
        }

        String sumAsString = String.valueOf(sum);

        while (sumAsString.length() < this.getNumberOfDigits()) {
            sumAsString = "0" + sumAsString;
        }

        return sumAsString;
    }
}
