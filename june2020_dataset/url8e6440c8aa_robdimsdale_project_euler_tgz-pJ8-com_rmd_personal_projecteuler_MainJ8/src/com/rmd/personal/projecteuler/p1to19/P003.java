package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

public class P003 implements Problem {

    private static final long DEFAULT_NUMBER_VALUE = 600851475143L;
    private long numberValue;

    public P003() {
        this.setNumberValue(DEFAULT_NUMBER_VALUE);
    }

    public long getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(long numberValue) {
        this.numberValue = numberValue;
    }

    @Override
    public String getDescription() {
        return "The prime factors of 13195 are 5, 7, 13 and 29.\n\n"
                + "What is the largest prime factor of the number 600851475143 ?";
    }

    @Override
    public String run() {
        long currentNumber = this.getNumberValue();

        long currentPrime = 2;

        while (currentPrime < currentNumber) {
            if (currentNumber % currentPrime == 0) {
                currentNumber = currentNumber / currentPrime;
            } else {
                currentPrime = Common.getPrimes().get(Common.getPrimes().indexOf(currentPrime) + 1);
            }
        }

        return String.valueOf(currentPrime);
    }
}
