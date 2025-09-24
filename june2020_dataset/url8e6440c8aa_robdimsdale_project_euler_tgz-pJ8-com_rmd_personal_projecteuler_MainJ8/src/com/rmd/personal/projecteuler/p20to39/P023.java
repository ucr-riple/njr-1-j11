package com.rmd.personal.projecteuler.p20to39; // SUPPRESS CHECKSTYLE fileLength

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

public class P023 implements Problem {

    private static final int DEFAULT_END = 28123;

    private int end;

    private boolean[] abundant;
    private boolean[] abundantSum;

    public P023() {
        this.setEnd(DEFAULT_END);

        this.abundant = new boolean[this.getEnd()];
        this.abundantSum = new boolean[this.getEnd()];
    }

    private int getEnd() {
        return end;
    }

    private void setEnd(int end) {
        this.end = end;
    }

    private boolean[] getAbundant() {
        return abundant;
    }

    private boolean[] getAbundantSum() {
        return abundantSum;
    }

    private void populateAbundantNumbers() {
        for (int i = 1; i < getAbundant().length - 1; i++) {
            if (this.isAbundant(i)) {
                this.getAbundant()[i] = true;
                for (int j = 0; j <= i; j++) {
                    if (getAbundant()[j] && (i + j < this.getEnd())) {
                        this.getAbundantSum()[i + j] = true;
                    }
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "A perfect number is a number for which the sum of its proper divisors is exactly equal to the number."
                + " For example, the sum of the proper divisors of 28 would be 1 + 2 + 4 + 7 + 14 = 28, "
                + "which means that 28 is a perfect number.\n\n"
                + "A number n is called deficient if the sum of its proper divisors is less than n and it is called "
                + "abundant if this sum exceeds n.\n\n"
                + "As 12 is the smallest abundant number, 1 + 2 + 3 + 4 + 6 = 16, the smallest number that can be "
                + "written as the sum of two abundant numbers is 24. By mathematical analysis, "
                + "it can be shown that all integers greater than 28123 can be written as the sum of two abundant "
                + "numbers. However, this upper limit cannot be reduced any further by analysis even though it is known"
                + " that the greatest number that cannot be expressed as the sum of two abundant numbers is less than "
                + "this limit.\n\n"
                + "Find the sum of all the positive integers "
                + "which cannot be written as the sum of two abundant numbers.";
    }

    @Override
    public String run() {
        this.populateAbundantNumbers();

        long sum = 0;

        for (int i = 0; i < this.getEnd(); i++) {
            if (!this.getAbundantSum()[i]) {
                sum += i;
            }
        }

        return String.valueOf(sum);
    }

    private boolean isAbundant(int value) {
        return Common.sumOfFactorsPrime(value) > value;
    }
}
