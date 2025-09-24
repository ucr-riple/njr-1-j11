package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

public class P004 implements Problem {

    private static final int DEFAULT_NUMBER_OF_DIGITS = 3;
    private int numberOfDigits;

    public P004() {
        this.setNumberOfDigits(DEFAULT_NUMBER_OF_DIGITS);
    }

    public int getNumberOfDigits() {
        return numberOfDigits;
    }

    public void setNumberOfDigits(int numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    @Override
    public String getDescription() {
        return "A palindromic number reads the same both ways. The largest palindrome made from the product of two "
                + "2-digit numbers is 9009 = 91 × 99.\n\n"
                + "Find the largest palindrome made from the product of two 3-digit numbers.";
    }

    @Override
    public String run() {

        return String.valueOf(this.findLargestPalindrome());
    }

    private long findLargestPalindrome() {
        long startVal = (long) Math.pow(10, this.getNumberOfDigits()) - 1;

        long largestPalindrome = 0;

        for (long numberA = startVal; numberA > 0; numberA--) {

            if (largestPalindrome > numberA * numberA) {
                return largestPalindrome;
            }

            for (long numberB = numberA; numberB > 0; numberB--) {
                long product = numberA * numberB;

                if (product < largestPalindrome) {
                    break;
                }

                if (Common.isPalindromeNumber(product)) {
                    largestPalindrome = product;
                    break;
                }

            }
        }

        return -1;
    }
}
