package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

import java.util.HashMap;
import java.util.Map;

public class P005 implements Problem {

    private static final int DEFAULT_MAX_FACTOR = 20;
    private int maxFactor;

    public P005() {
        this.setMaxFactor(DEFAULT_MAX_FACTOR);
    }

    public int getMaxFactor() {
        return maxFactor;
    }

    public void setMaxFactor(int maxFactor) {
        this.maxFactor = maxFactor;
    }

    @Override
    public String getDescription() {
        return "2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any "
                + "remainder.\n\n"
                + "What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?";
    }

    @Override
    public String run() {
        Map<Long, Integer> primePowers = new HashMap<Long, Integer>();

        long currentPrime = 2;
        while (currentPrime < this.getMaxFactor()) {
            int currentPrimePower = 0;
            while (Math.pow(currentPrime, currentPrimePower + 1) < this.getMaxFactor()) {
                currentPrimePower++;
            }
            if (primePowers.get(currentPrime) == null || primePowers.get(currentPrime) < currentPrimePower) {
                primePowers.put(currentPrime, currentPrimePower);
            }
            currentPrime = Common.getPrimes().get(Common.getPrimes().indexOf(currentPrime) + 1);
        }

        double product = 1;
        for (long prime : primePowers.keySet()) {
            product = product * Math.pow(prime, primePowers.get(prime));
        }

        return String.valueOf((long) product);
    }
}
