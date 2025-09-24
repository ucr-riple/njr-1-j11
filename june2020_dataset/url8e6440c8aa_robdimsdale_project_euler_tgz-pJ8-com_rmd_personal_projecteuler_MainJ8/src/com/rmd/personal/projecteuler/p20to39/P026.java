package com.rmd.personal.projecteuler.p20to39; // SUPPRESS CHECKSTYLE fileLength

import com.rmd.personal.projecteuler.Problem;

import java.util.HashMap;
import java.util.Map;

public class P026 implements Problem {

    private static final int DEFAULT_MAX_D = 1000;

    private int maxD;

    public P026() {
        this.setMaxD(DEFAULT_MAX_D);
    }

    public int getMaxD() {
        return maxD;
    }

    public void setMaxD(int maxD) {
        this.maxD = maxD;
    }

    @Override
    public String getDescription() {
        return "A unit fraction contains 1 in the numerator."
                + " The decimal representation of the unit fractions with denominators 2 to 10 are given:\n\n"
                + "1/2\t= \t0.5\n"
                + "1/3\t= \t0.(3)\n"
                + "1/4\t= \t0.25\n"
                + "1/5\t= \t0.2\n"
                + "1/6\t= \t0.1(6)\n"
                + "1/7\t= \t0.(142857)\n"
                + "1/8\t= \t0.125\n"
                + "1/9\t= \t0.(1)\n"
                + "1/10\t= \t0.1\n"
                + "Where 0.1(6) means 0.166666..., and has a 1-digit recurring cycle."
                + " It can be seen that 1/7 has a 6-digit recurring cycle.\n\n"
                + "Find the value of d < 1000 for which 1/d contains the longest recurring cycle"
                + " in its decimal fraction part.";
    }

    @Override
    public String run() {
        int maxRecurringLength = 0;
        int maxRecurringLengthIndex = 0;
        for (int d = 1; d < this.getMaxD(); d++) {
            int currentRecurringLength = this.determineCycleLengthInDecimalPart(d);
            if (currentRecurringLength > maxRecurringLength) {
                maxRecurringLength = currentRecurringLength;
                maxRecurringLengthIndex = d;
            }
        }

        return String.valueOf(maxRecurringLengthIndex);
    }

    private int determineCycleLengthInDecimalPart(int i) {
        Map<Integer, Integer> stateToDecimalDigitIndex = new HashMap<Integer, Integer>();
        int state = 1;
        int decimalDigitIndex = 0;
        while (!stateToDecimalDigitIndex.containsKey(state)) {
            stateToDecimalDigitIndex.put(state, decimalDigitIndex);
            state = (state * 10) % i;
            decimalDigitIndex++;
        }
        return decimalDigitIndex - stateToDecimalDigitIndex.get(state);
    }
}
