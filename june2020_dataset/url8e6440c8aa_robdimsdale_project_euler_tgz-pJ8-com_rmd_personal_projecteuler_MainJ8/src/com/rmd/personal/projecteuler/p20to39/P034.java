package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.Problem;

public class P034 implements Problem {

    private static final int DEFAULT_START = 3;
    private static final int DEFAULT_END = 2540160;

    private int start;
    private int end;

    private long[] singleDigitFactorials;

    public P034() {
        this.setStart(DEFAULT_START);
        this.setEnd(DEFAULT_END);

        this.singleDigitFactorials = new long[10];
        this.getSingleDigitFactorials()[0] = 1;
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

    private long[] getSingleDigitFactorials() {
        return singleDigitFactorials;
    }

    @Override
    public String getDescription() {
        return "145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.\n\n"
                + "Find the sum of all numbers which are equal to the sum of the factorial of their digits.\n\n"
                + "Note: as 1! = 1 and 2! = 2 are not sums they are not included.";
    }

    @Override
    public String run() {
        long sumOfCuriousNumbers = 0;

        for (int i = this.getStart(); i <= this.getEnd(); i++) {
            long currentSum = 0;
            int tmpI = i;
            while (tmpI > 0) {
                currentSum += this.getFactorialForDigit(tmpI % 10);
                tmpI /= 10;
            }
            if (i == currentSum) {
                sumOfCuriousNumbers += currentSum;
            }
        }

        return String.valueOf(sumOfCuriousNumbers);
    }

    private long getFactorialForDigit(int digit) {
        if (this.getSingleDigitFactorials()[digit] != 0) {
            return this.getSingleDigitFactorials()[digit];
        }
        long factorial = 1;
        int digitCopy = digit;
        while (digitCopy > 0) {
            factorial *= digitCopy;
            digitCopy--;
        }
        this.getSingleDigitFactorials()[digit] = factorial;
        return factorial;
    }
}
