package com.rmd.personal.projecteuler.p20to39; // SUPPRESS CHECKSTYLE fileLength

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

public class P027 implements Problem {

    private static final int DEFAULT_MAX_A = 1000;
    private static final int DEFAULT_MAX_B = 1000;

    private int maxA;
    private int maxB;

    public P027() {
        this.setMaxA(DEFAULT_MAX_A);
        this.setMaxB(DEFAULT_MAX_B);
    }

    public int getMaxA() {
        return maxA;
    }

    public void setMaxA(int maxA) {
        this.maxA = maxA;
    }

    public int getMaxB() {
        return maxB;
    }

    public void setMaxB(int maxB) {
        this.maxB = maxB;
    }

    @Override
    public String getDescription() {
        return "Euler discovered the remarkable quadratic formula:\n\n"
                + "n² + n + 41\n\n"
                + "It turns out that the formula will produce 40 primes for the consecutive values n = 0 to 39. "
                + "However, when n = 40, 402 + 40 + 41 = 40(40 + 1) + 41 is divisible by 41, "
                + "and certainly when n = 41, 41² + 41 + 41 is clearly divisible by 41.\n\n"
                + "The incredible formula  n² − 79n + 1601 was discovered, which produces 80 primes for the "
                + "consecutive values n = 0 to 79. The product of the coefficients, −79 and 1601, is −126479.\n\n"
                + "Considering quadratics of the form:\n\n"
                + "n² + an + b, where |a| < 1000 and |b| < 1000\n\n"
                + "where |n| is the modulus/absolute value of n\n"
                + "e.g. |11| = 11 and |−4| = 4\n"
                + "Find the product of the coefficients, a and b, "
                + "for the quadratic expression that produces the maximum number of primes for consecutive values of n,"
                + " starting with n = 0.";
    }

    @Override
    public String run() {
        int maxPrimes = 0;
        long maxPrimesABProduct = 0;

        for (int a = 1 - this.getMaxA(); a < this.getMaxA(); a++) {
            for (int b = 1 - this.getMaxB(); b < this.getMaxB(); b++) {
                int n = 0;
                long possiblePrime = (n * n) + (a * n) + b;
                while (Common.getPrimesAsSet().contains(possiblePrime)) {
                    n++;
                    possiblePrime = (n * n) + (a * n) + b;
                }
                if (n - 1 > maxPrimes) {
                    maxPrimes = n - 1;
                    maxPrimesABProduct = a * b;
                }
            }
        }

        return String.valueOf(maxPrimesABProduct);
    }
}
