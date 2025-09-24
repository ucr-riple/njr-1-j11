package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

import java.util.HashMap;
import java.util.Map;

public class P021 implements Problem {

    private static final int DEFAULT_END = 10000;

    private long end;

    private Map<Long, Long> valueToDivisorSum;

    public P021() {
        this.setEnd(DEFAULT_END);

        this.valueToDivisorSum = new HashMap<Long, Long>();
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    private Map<Long, Long> getValueToDivisorSum() {
        return valueToDivisorSum;
    }

    @Override
    public String getDescription() {
        return "Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into "
                + "n).\n"
                + "If d(a) = b and d(b) = a, where a ≠ b, then a and b are an amicable pair and each of a and b are "
                + "called amicable numbers.\n\n"
                + "For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, "
                + "55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, "
                + "71 and 142; so d(284) = 220.\n\n"
                + "Evaluate the sum of all the amicable numbers under 10000.";
    }

    @Override
    public String run() {
        long sum = 0;

        for (int i = 2; i <= this.getEnd(); i++) {
            long factorSum = Common.sumOfFactorsPrime(i);
            if (factorSum > i) {
                this.getValueToDivisorSum().put((long) i, factorSum);
            } else if (factorSum < i) {
                if (this.getValueToDivisorSum().containsKey(factorSum)) {
                    if (this.getValueToDivisorSum().get(factorSum) == i) {
                        sum = sum + i + factorSum;
                    }
                }
            }
        }

        return String.valueOf(sum);
    }
}
