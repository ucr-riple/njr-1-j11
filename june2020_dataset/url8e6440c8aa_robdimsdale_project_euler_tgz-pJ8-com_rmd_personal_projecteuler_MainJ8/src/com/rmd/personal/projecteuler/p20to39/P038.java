package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

public class P038 implements Problem {

    private static final int DEFAULT_START = 1;
    private static final int DEFAULT_END = 9999;

    private int start;
    private int end;

    public P038() {
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
        return "Take the number 192 and multiply it by each of 1, 2, and 3:\n\n"
                + "192 × 1 = 192\n"
                + "192 × 2 = 384\n"
                + "192 × 3 = 576\n"
                + "By concatenating each product we get the 1 to 9 pandigital, 192384576. "
                + "We will call 192384576 the concatenated product of 192 and (1,2,3)\n\n"
                + "The same can be achieved by starting with 9 and multiplying by 1, 2, 3, 4, and 5, "
                + "giving the pandigital, 918273645, which is the concatenated product of 9 and (1,2,3,4,5).\n\n"
                + "What is the largest 1 to 9 pandigital 9-digit number that can be formed as the "
                + "concatenated product of an integer with (1,2, ... , n) where n > 1?";
    }

    @Override
    public String run() {
        final long smallest9DigitNumber = 100000000L;
        final long largest9DigitNumber = 999999999L;

        long largestPandigital = 0;

        for (int i = this.getStart(); i <= this.getEnd(); i++) {
            long concatenation = i;
            for (int j = 2; j <= 9; j++) {
                concatenation = Common.concatenateNumbers(concatenation, i * j);
                if (concatenation > largest9DigitNumber) {
                    break;
                }

                if (concatenation < smallest9DigitNumber) {
                    continue;
                }

                final int maxDigit = 9;
                if (concatenation > largestPandigital
                        && Common.isPandigital(concatenation, maxDigit)) {
                    largestPandigital = concatenation;
                }
            }
        }

        return String.valueOf(largestPandigital);
    }
}