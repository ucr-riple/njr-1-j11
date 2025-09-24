package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

public class P037 implements Problem {

    private static final int DEFAULT_START_PRIME = 11;
    private static final int DEFAULT_COUNT = 11;

    private int startPrime;
    private int count;

    public P037() {
        this.setStartPrime(DEFAULT_START_PRIME);
        this.setCount(DEFAULT_COUNT);
    }

    public int getStartPrime() {
        return startPrime;
    }

    public void setStartPrime(int startPrime) {
        this.startPrime = startPrime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String getDescription() {
        return "The number 3797 has an interesting property. Being prime itself, it is possible to continuously "
                + "remove digits from left to right, and remain prime at each stage: 3797, 797, 97, "
                + "and 7. Similarly we can work from right to left: 3797, 379, 37, and 3.\n\n"
                + "Find the sum of the only eleven primes that are both truncatable from left to right and right to "
                + "left.\n\n"
                + "NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes.";
    }

    @Override
    public String run() {
        long sum = 0;
        int primeIndex = Common.getPrimes().indexOf((long) this.getStartPrime());
        int currentCount = 0;
        while (currentCount < this.getCount()) {
            long currentPrime = Common.getPrimes().get(primeIndex);
            if (this.bidirectionalTruncatablePrime(currentPrime)) {
                currentCount++;
                sum += currentPrime;
            }
            primeIndex++;
        }

        return String.valueOf(sum);
    }

    private boolean bidirectionalTruncatablePrime(long prime) {
        long factorOfTen = 10;
        while (factorOfTen < prime) {
            if (!Common.getPrimesAsSet().contains(prime % factorOfTen)) {
                return false;
            }
            if (!Common.getPrimesAsSet().contains(prime / factorOfTen)) {
                return false;
            }
            factorOfTen *= 10;
        }
        return true;
    }
}