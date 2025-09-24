package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Common;
import com.rmd.personal.projecteuler.Problem;

import java.math.BigInteger;

public class P016 implements Problem {

    private static final int DEFAULT_POWER = 1000;

    private int power;

    public P016() {
        this.setPower(DEFAULT_POWER);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String getDescription() {
        return "2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.\n\n"
                + "What is the sum of the digits of the number 2^1000?";
    }

    @Override
    public String run() {
        BigInteger bigInteger = new BigInteger("2");
        bigInteger = bigInteger.pow(this.getPower());
        return String.valueOf(Common.sumDigitsInString(String.valueOf(bigInteger)));
    }
}
