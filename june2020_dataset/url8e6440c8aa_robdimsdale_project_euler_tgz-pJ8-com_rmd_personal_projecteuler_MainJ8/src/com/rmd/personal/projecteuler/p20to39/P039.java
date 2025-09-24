package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.Problem;

public class P039 implements Problem {

    private static final int DEFAULT_START = 1;
    private static final int DEFAULT_END = 999;

    private int start;
    private int end;

    public P039() {
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
        return "If p is the perimeter of a right angle triangle with integral length sides, {a,b,c}, "
                + "there are exactly three solutions for p = 120.\n\n"
                + "{20,48,52}, {24,45,51}, {30,40,50}\n\n"
                + "For which value of p ≤ 1000, is the number of solutions maximised?";
    }

    @Override
    public String run() {
        int maxP = 0;
        int maxPCount = 0;

        for (int p = this.getStart(); p <= this.getEnd(); p++) {
            int currentPCount = 0;
            for (int a = 1; a < p; a++) {
                if ((p * (p - 2 * a)) % (2 * (p - a)) == 0) {
                    currentPCount++;
                }
            }
            if (currentPCount > maxPCount) {
                maxPCount = currentPCount;
                maxP = p;
            }
        }

        return String.valueOf(maxP);
    }
}