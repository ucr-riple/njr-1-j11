package com.rmd.personal.projecteuler;

public class P043 implements Problem {

    @Override
    public String getDescription() {
        return "The number, 1406357289, is a 0 to 9 pandigital number because it is made up of each of the digits 0 "
                + "to 9 in some order, but it also has a rather interesting sub-string divisibility property.\n\n"
                + "Let d1 be the 1st digit, d2 be the 2nd digit, and so on. In this way, we note the following:\n\n"
                + "d2d3d4=406 is divisible by 2\n"
                + "d3d4d5=063 is divisible by 3\n"
                + "d4d5d6=635 is divisible by 5\n"
                + "d5d6d7=357 is divisible by 7\n"
                + "d6d7d8=572 is divisible by 11\n"
                + "d7d8d9=728 is divisible by 13\n"
                + "d8d9d10=289 is divisible by 17\n"
                + "Find the sum of all 0 to 9 pandigital numbers with this property.";
    }

    @Override
    public String run() {
        long sum = 0;

        int[] perm = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        for (long it = 0; it < 9L * Common.factorial(9); it++) {

            final int first3StringStartIndex = 1;
            final int last3StringStartIndex = 7;
            boolean fullyDividablePandigital = true;
            for (int j = first3StringStartIndex; j <= last3StringStartIndex; j++) {
                long prime = Common.getPrimes().get(j - 1);
                if (!(this.get3DigitsSubStringStartingAtN(perm, j) % prime == 0)) {
                    fullyDividablePandigital = false;
                    break;
                }
            }
            if (fullyDividablePandigital) {
                sum += Common.getLongFromDigitArray(perm);
            }

            perm = Common.nextPermutation(perm);
        }

        return String.valueOf(sum);
    }

    private int get3DigitsSubStringStartingAtN(int[] number, int n) {
        long subString = number[n];
        subString = Common.concatenateNumbers(subString, number[n + 1]);
        subString = Common.concatenateNumbers(subString, number[n + 2]);
        return (int) subString;
    }
}
