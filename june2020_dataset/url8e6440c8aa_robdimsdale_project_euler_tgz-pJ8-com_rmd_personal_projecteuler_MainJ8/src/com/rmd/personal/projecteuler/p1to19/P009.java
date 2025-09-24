package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Problem;

public class P009 implements Problem {

    private static final int DEFAULT_TRIPLET_SUM = 1000;
    private int tripletSum;

    public P009() {
        this.setTripletSum(DEFAULT_TRIPLET_SUM);
    }

    public int getTripletSum() {
        return tripletSum;
    }

    public void setTripletSum(int tripletSum) {
        this.tripletSum = tripletSum;
    }

    @Override
    public String getDescription() {
        return "A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,\n\n"
                + "a2 + b2 = c2\n"
                + "For example, 3^2 + 4^2 = 9 + 16 = 25 = 5^2.\n\n"
                + "There exists exactly one Pythagorean triplet for which a + b + c = 1000.\n"
                + "Find the product abc.";
    }

    @Override
    public String run() {

        long abc = this.findProduct();

        return String.valueOf(abc);
    }

    private long findProduct() {
        int maxA = (int) Math.floor((this.getTripletSum() - 1) / 2.0);
        int maxB = (int) Math.floor(this.getTripletSum() / 2.0);

        for (int a = 1; a < maxA; a++) {
            for (int b = a + 1; b < maxB; b++) {
                int c = this.getTripletSum() - a - b;
                if ((long) (Math.pow(a, 2) + Math.pow(b, 2)) == (long) Math.pow(c, 2)) {
                    return a * b * c;
                }
            }
        }
        return -1;
    }
}
