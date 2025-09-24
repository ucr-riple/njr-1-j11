package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

import java.util.ArrayList;
import java.util.List;

public class P033 implements Problem {

    private static final int DEFAULT_START = 11;
    private static final int DEFAULT_NUMERATOR_END = 98;
    private static final int DEFAULT_DENOMINATOR_END = 99;

    private int numeratorStart;
    private int numeratorEnd;
    private int denominatorEnd;

    public P033() {
        this.setNumeratorStart(DEFAULT_START);
        this.setNumeratorEnd(DEFAULT_NUMERATOR_END);
        this.setDenominatorEnd(DEFAULT_DENOMINATOR_END);
    }

    public int getNumeratorStart() {
        return numeratorStart;
    }

    public void setNumeratorStart(int numeratorStart) {
        this.numeratorStart = numeratorStart;
    }

    public int getNumeratorEnd() {
        return numeratorEnd;
    }

    public void setNumeratorEnd(int start) {
        this.numeratorEnd = start;
    }

    public int getDenominatorEnd() {
        return denominatorEnd;
    }

    public void setDenominatorEnd(int denominatorEnd) {
        this.denominatorEnd = denominatorEnd;
    }

    @Override
    public String getDescription() {
        return "The fraction 49/98 is a curious fraction, as an inexperienced mathematician in attempting to "
                + "simplify it may incorrectly believe that 49/98 = 4/8, which is correct, is obtained by cancelling "
                + "the 9s.\n\n"
                + "We shall consider fractions like, 30/50 = 3/5, to be trivial examples.\n\n"
                + "There are exactly four non-trivial examples of this type of fraction, less than one in value, and "
                + "containing two digits in the numerator and denominator.\n\n"
                + "If the product of these four fractions is given in its lowest common terms, "
                + "find the value of the denominator.";
    }

    @Override
    public String run() {
        List<Integer> numerators = new ArrayList<Integer>();
        List<Integer> denominators = new ArrayList<Integer>();

        for (int i = this.getNumeratorStart(); i <= this.getNumeratorEnd(); i++) {
            if (i % 10 == 0) {
                continue;
            }
            for (int j = i + 1; j <= this.getDenominatorEnd(); j++) {
                if (j % 10 == 0) {
                    continue;
                }
                int[] possibleCuriousFraction = this.possibleCuriousFraction(i, j);
                if (possibleCuriousFraction.length != 0) {
                    numerators.add(possibleCuriousFraction[0]);
                    denominators.add(possibleCuriousFraction[1]);
                }
            }
        }

        long numeratorProduct = 1;
        for (long product : numerators) {
            numeratorProduct *= product;
        }

        long denominatorProduct = 1;
        for (long product : denominators) {
            denominatorProduct *= product;
        }

        int primeIndex = 0;
        long prime = Common.getPrimes().get(primeIndex);
        while (prime <= numeratorProduct) {
            if (numeratorProduct % prime == 0 && denominatorProduct % prime == 0) {
                numeratorProduct /= prime;
                denominatorProduct /= prime;
            } else {
                primeIndex++;
                prime = Common.getPrimes().get(primeIndex);
            }
        }

        return String.valueOf(denominatorProduct);
    }

    private int[] possibleCuriousFraction(int numerator, int denominator) {
        double originalValue = (double) numerator / (double) denominator;

        int[] numeratorDigits = {(numerator / 10) % 10, numerator % 10};
        int[] denominatorDigits = {(denominator / 10) % 10, denominator % 10};

        int cancelledNumeratorDigit = 0;
        int cancelledDenominatorDigit = 0;
        for (int i = 0; i < 2; i++) {
            if (numeratorDigits[i] == denominatorDigits[i]) {
                cancelledNumeratorDigit = numeratorDigits[1 - i];
                cancelledDenominatorDigit = denominatorDigits[1 - i];
            }
            if (numeratorDigits[i] == denominatorDigits[1 - i]) {
                cancelledNumeratorDigit = numeratorDigits[1 - i];
                cancelledDenominatorDigit = denominatorDigits[i];
            }
        }
        double cancelledValue = (double) cancelledNumeratorDigit / (double) cancelledDenominatorDigit;
        if (this.doublesEqual(cancelledValue, originalValue)) {
            return new int[]{cancelledNumeratorDigit, cancelledDenominatorDigit};
        }
        return new int[]{};
    }

    private boolean doublesEqual(double a, double b) {
        final double margin = 0.0000001;
        return (Math.abs(a - b) < margin);
    }
}
