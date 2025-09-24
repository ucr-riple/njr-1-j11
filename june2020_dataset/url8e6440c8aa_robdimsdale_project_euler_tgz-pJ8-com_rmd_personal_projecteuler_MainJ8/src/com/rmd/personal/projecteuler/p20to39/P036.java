package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

public class P036 implements Problem {

    private static final int DEFAULT_START = 1;
    private static final int DEFAULT_END = 1000000;

    private int start;
    private int end;

    public P036() {
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
        return "The decimal number, 585 = 10010010012 (binary), is palindromic in both bases.\n\n"
                + "Find the sum of all numbers, less than one million, which are palindromic in base 10 and base 2.\n\n"
                + "(Please note that the palindromic number, in either base, may not include leading zeros.)";
    }

    @Override
    public String run() {
        long sum = 0;

        for (int i = this.getStart(); i <= this.getEnd(); i++) {
            if (Common.isPalindromeNumber(i) && Common.isPalindromeNumber(Common.getNumberAsBinaryString(i))) {
                sum += i;
            }
        }

        return String.valueOf(sum);
    }
}
