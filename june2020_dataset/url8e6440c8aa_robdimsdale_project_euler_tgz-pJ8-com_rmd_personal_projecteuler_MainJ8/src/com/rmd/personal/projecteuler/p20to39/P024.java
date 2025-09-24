package com.rmd.personal.projecteuler.p20to39; // SUPPRESS CHECKSTYLE fileLength

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

import java.util.ArrayList;
import java.util.List;

public class P024 implements Problem {

    private static final int DEFAULT_NUMBER_OF_DIGITS = 10;
    private static final int DESIRED_PERMUTATION_INDEX = 1000000;

    private int numberOfDigits;
    private int desiredPermutationIndex;

    private List<Integer> desiredPermutation;
    private List<Integer> remainingDigits;

    private int currentLowerBoundOnIndex;

    public P024() {
        this.setNumberOfDigits(DEFAULT_NUMBER_OF_DIGITS);
        this.setDesiredPermutationIndex(DESIRED_PERMUTATION_INDEX);

        this.setCurrentLowerBoundOnIndex(1);

        this.desiredPermutation = new ArrayList<Integer>();
    }

    public int getNumberOfDigits() {
        return numberOfDigits;
    }

    public void setNumberOfDigits(int numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    public int getDesiredPermutationIndex() {
        return desiredPermutationIndex;
    }

    public void setDesiredPermutationIndex(int desiredPermutationIndex) {
        this.desiredPermutationIndex = desiredPermutationIndex;
    }

    private List<Integer> getDesiredPermutation() {
        return desiredPermutation;
    }

    private List<Integer> getRemainingDigits() {
        return remainingDigits;
    }

    private int getCurrentLowerBoundOnIndex() {
        return currentLowerBoundOnIndex;
    }

    private void setCurrentLowerBoundOnIndex(int currentLowerBoundOnIndex) {
        this.currentLowerBoundOnIndex = currentLowerBoundOnIndex;
    }

    private void initializeRemainingDigits() {
        this.remainingDigits = new ArrayList<Integer>();
        for (int i = 0; i < this.getNumberOfDigits(); i++) {
            this.getRemainingDigits().add(i);
        }
    }

    @Override
    public String getDescription() {
        return "A permutation is an ordered arrangement of objects. "
                + "For example, 3124 is one possible permutation of the digits 1, 2, 3 and 4."
                + "If all of the permutations are listed numerically or alphabetically, "
                + "we call it lexicographic order. The lexicographic permutations of 0, 1 and 2 are:\n\n"
                + "012   021   102   120   201   210\n\n"
                + "What is the millionth lexicographic permutation of the digits 0, 1, 2, 3, 4, 5, 6, 7, 8 and 9?";
    }

    @Override
    public String run() {
        this.initializeRemainingDigits();

        // Whilst we still have two remaining digits, figure out the ordering
        while (this.getRemainingDigits().size() > 1) {
            // Iterate over the remaining digits
            List<Integer> remainingDigitsCopy = new ArrayList<Integer>(this.getRemainingDigits());
            for (int i = 0; i < remainingDigitsCopy.size(); i++) {
                int optionsAtDigit
                        = (int) Common.factorial(this.getNumberOfDigits() - this.getDesiredPermutation().size() - 1);
                int lowerBound = this.getCurrentLowerBoundOnIndex() + i * optionsAtDigit;
                int upperBound = this.getCurrentLowerBoundOnIndex() + (i + 1) * optionsAtDigit - 1;

                if (lowerBound <= this.getDesiredPermutationIndex()
                        && upperBound >= this.getDesiredPermutationIndex()) {
                    this.getDesiredPermutation().add(remainingDigitsCopy.get(i));
                    // Remove the element at the selected index, not the selected index itself!
                    // Compare List<Integer>.remove(int) vs List<Integer>.remove(Integer).
                    this.getRemainingDigits().remove(i);
                    this.setCurrentLowerBoundOnIndex(lowerBound);
                    break;
                }
            }
        }

        // We only have one remaining digit - add it.
        this.getDesiredPermutation().add(this.getRemainingDigits().get(0));
        // For completeness, remove from the remaining digit list.
        this.getRemainingDigits().remove(0);

        StringBuilder stringBuilder = new StringBuilder();
        for (int digit : this.getDesiredPermutation()) {
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
}
