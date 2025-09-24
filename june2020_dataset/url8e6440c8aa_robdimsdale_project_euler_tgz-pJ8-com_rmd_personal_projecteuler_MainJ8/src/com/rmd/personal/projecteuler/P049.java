package com.rmd.personal.projecteuler;

public class P049 implements Problem {

    private static final int DEFAULT_END = 9999;
    private static final int DEFAULT_START = 1488;

    private int start;
    private int end;

    public P049() {
        this.setStart(DEFAULT_START);
        this.setEnd(DEFAULT_END);
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String getDescription() {
        return "The arithmetic sequence, 1487, 4817, 8147, in which each of the terms increases by 3330, "
                + "is unusual in two ways: (i) each of the three terms are prime, and, "
                + "(ii) each of the 4-digit numbers are permutations of one another.\n\n"
                + "There are no arithmetic sequences made up of three 1-, 2-, or 3-digit primes, "
                + "exhibiting this property, but there is one other 4-digit increasing sequence.\n\n"
                + "What 12-digit number do you form by concatenating the three terms in this sequence?";
    }

    @Override
    public String run() {
        long found1 = 0;
        long found2 = 0;
        long found3 = 0;

        boolean found = false;

        while (!found) {
            for (long possible1 : Common.getPrimes()) {
                if (found) {
                    break;
                }

                if (possible1 < this.getStart()) {
                    continue;
                }

                if (possible1 > this.getEnd()) {
                    throw new RuntimeException("Prime list should have been found at four digits!");
                }

                int[] digits1 = Common.getDigitArrayFromLong(possible1);

                int[] digits2 = Common.getDigitArrayFromLong(possible1);
                digits2 = Common.nextPermutation(digits2);

                if (digits2.length == digits1.length) {
                    long possible2 = Common.getLongFromDigitArray(digits2);

                    while (!found && possible2 != possible1) {
                        if (Common.getPrimesAsSet().contains(possible2)) {
                            int[] digits3 = Common.getDigitArrayFromLong(possible2);
                            digits3 = Common.nextPermutation(digits3);
                            long possible3 = Common.getLongFromDigitArray(digits3);

                            if (digits3.length == digits2.length) {
                                while (!found && possible3 != possible2 && possible3 != possible1) {
                                    if (Common.getPrimesAsSet().contains(possible3)) {
                                        if (possible3 - possible2 == possible2 - possible1) {
                                            found1 = possible1;
                                            found2 = possible2;
                                            found3 = possible3;
                                            found = true;
                                        }
                                    }

                                    digits3 = Common.nextPermutation(digits3);
                                    possible3 = Common.getLongFromDigitArray(digits3);
                                }
                            }
                        }

                        digits2 = Common.nextPermutation(digits2);
                        possible2 = Common.getLongFromDigitArray(digits2);
                    }
                }
            }
        }

        long concatenated = Common.concatenateNumbers(Common.concatenateNumbers(found1, found2), found3);

        return String.valueOf(concatenated);
    }
}
