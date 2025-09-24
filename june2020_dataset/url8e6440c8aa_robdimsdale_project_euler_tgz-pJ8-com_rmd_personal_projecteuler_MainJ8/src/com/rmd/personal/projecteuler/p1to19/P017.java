package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Problem;

import java.util.HashMap;
import java.util.Map;

public class P017 implements Problem {

    private static final int DEFAULT_START = 1;
    private static final int DEFAULT_END = 1000;

    private int start;
    private int end;

    private Map<Integer, String> digitToWord;

    public P017() {
        this.setStart(DEFAULT_START);
        this.setEnd(DEFAULT_END);

        this.digitToWord = new HashMap<Integer, String>();
        this.digitToWord.put(0, "");
        this.digitToWord.put(1, "one");
        this.digitToWord.put(2, "two");
        this.digitToWord.put(3, "three");
        this.digitToWord.put(4, "four");
        this.digitToWord.put(5, "five");
        this.digitToWord.put(6, "six");
        this.digitToWord.put(7, "seven");
        this.digitToWord.put(8, "eight");
        this.digitToWord.put(9, "nine");
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

    private Map<Integer, String> getDigitToWord() {
        return this.digitToWord;
    }

    @Override
    public String getDescription() {
        return "If the numbers 1 to 5 are written out in words: one, two, three, four, five, "
                + "then there are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.\n\n"
                + "If all the numbers from 1 to 1000 (one thousand) inclusive were written out in words, "
                + "how many letters would be used?\n\n\n"
                + "NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and forty-two) contains 23 "
                + "letters and 115 (one hundred and fifteen) contains 20 letters. "
                + "The use of \"and\" when writing out numbers is in compliance with British usage.";
    }

    @Override
    public String run() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = this.getStart(); i <= this.getEnd(); i++) {
            int currentVal = i;
            if (i >= 100) {
                stringBuilder.append(this.getDigitToWord().get(i / 100)); // SUPPRESS CHECKSTYLE lineLength
                stringBuilder.append("hundred");
                if (currentVal % 100 != 0) {
                    stringBuilder.append("and");
                }
                while (currentVal >= 100) {
                    currentVal -= 100;
                }
            }

            if (currentVal < 10) {
                stringBuilder.append(this.getDigitToWord().get(currentVal));
            } else if (currentVal >= 10 && currentVal < 20) {
                stringBuilder.append(this.handle10to20(currentVal));
            } else if (currentVal < 30) {
                stringBuilder.append("twenty");
                stringBuilder.append(this.getDigitToWord().get(currentVal - 20));
            } else if (currentVal < 40) {
                stringBuilder.append("thirty");
                stringBuilder.append(this.getDigitToWord().get(currentVal - 30));
            } else if (currentVal < 50) {
                stringBuilder.append("forty");
                stringBuilder.append(this.getDigitToWord().get(currentVal - 40));
            } else if (currentVal < 60) {
                stringBuilder.append("fifty");
                stringBuilder.append(this.getDigitToWord().get(currentVal - 50));
            } else if (currentVal < 70) {
                stringBuilder.append("sixty");
                stringBuilder.append(this.getDigitToWord().get(currentVal - 60));
            } else if (currentVal < 80) {
                stringBuilder.append("seventy");
                stringBuilder.append(this.getDigitToWord().get(currentVal - 70));
            } else if (currentVal < 90) {
                stringBuilder.append("eighty");
                stringBuilder.append(this.getDigitToWord().get(currentVal - 80));
            } else if (currentVal < 100) {
                stringBuilder.append("ninety");
                stringBuilder.append(this.getDigitToWord().get(currentVal - 90));
            }
        }
        return String.valueOf(stringBuilder.toString().length());
    }

    private String handle10to20(int currentVal) {
        switch (currentVal) {
            default:
                throw new IllegalArgumentException("Unrecognized value: " + currentVal);
            case 10:
                return "ten";
            case 11:
                return "eleven";
            case 12:
                return "twelve";
            case 13:
                return "thirteen";
            case 14:
                return "fourteen";
            case 15:
                return "fifteen";
            case 16:
                return "sixteen";
            case 17:
                return "seventeen";
            case 18:
                return "eighteen";
            case 19:
                return "nineteen";
        }
    }
}
