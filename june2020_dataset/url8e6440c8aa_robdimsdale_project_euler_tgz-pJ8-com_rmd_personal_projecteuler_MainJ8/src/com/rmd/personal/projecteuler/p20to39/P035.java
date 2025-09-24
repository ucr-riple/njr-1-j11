package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

import java.util.HashSet;
import java.util.Set;

public class P035 implements Problem {

    private static final int DEFAULT_START = 2;
    private static final int DEFAULT_END = 1000000;

    private int start;
    private int end;

    private Set<Integer> knownCircularPrimes;

    public P035() {
        this.setStart(DEFAULT_START);
        this.setEnd(DEFAULT_END);

        this.knownCircularPrimes = new HashSet<Integer>();
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

    private Set<Integer> getKnownCircularPrimes() {
        return knownCircularPrimes;
    }

    @Override
    public String getDescription() {
        return "The number, 197, is called a circular prime because all rotations of the digits: 197, 971, and 719, "
                + "are themselves prime.\n\n"
                + "There are thirteen such primes below 100: 2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, and 97.\n\n"
                + "How many circular primes are there below one million?";
    }

    @Override
    public String run() {
        long circularPrimeCount = 0;

        for (int i = this.getStart(); i <= this.getEnd(); i++) {
            if (!Common.getPrimesAsSet().contains((long) i)
                    || this.getKnownCircularPrimes().contains(i)) {
                continue;
            }

            if (i < 10 && Common.getPrimesAsSet().contains((long) i)) {
                circularPrimeCount++;
                this.getKnownCircularPrimes().add(i);
                continue;
            }

            int currentRotation = this.getNextRotation(i);
            int currentRotationCount = 1;

            while (Common.getPrimesAsSet().contains((long) currentRotation)) {
                this.getKnownCircularPrimes().add(currentRotation);
                if (currentRotation == i) {
                    circularPrimeCount += currentRotationCount;
                    break;
                }
                currentRotationCount++;
                currentRotation = this.getNextRotation(currentRotation);
            }
        }

        return String.valueOf(circularPrimeCount);
    }

    private int getNextRotation(int currentRotation) {
        int i = currentRotation;
        int lastDigit = currentRotation % 10;
        currentRotation /= 10;
        int factorOf10 = 10;
        while (factorOf10 <= i) {
            factorOf10 *= 10;
            lastDigit *= 10;
        }
        currentRotation += lastDigit;

        return currentRotation;
    }
}
