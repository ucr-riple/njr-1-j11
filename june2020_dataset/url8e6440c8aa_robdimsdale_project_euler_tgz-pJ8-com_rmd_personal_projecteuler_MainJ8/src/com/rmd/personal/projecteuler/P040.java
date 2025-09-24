package com.rmd.personal.projecteuler;

import java.util.HashSet;
import java.util.Set;

public class P040 implements Problem {

    private static final int DEFAULT_MAX = 1000000;

    private int max;
    private Set<Integer> indices;

    public P040() {
        this.setMax(DEFAULT_MAX);
        this.setIndices(this.getDefaultIndices());
    }

    private Set<Integer> getDefaultIndices() {
        Set<Integer> defaultIndices = new HashSet<Integer>();
        defaultIndices.add(1);
        defaultIndices.add(10);
        defaultIndices.add(100);
        defaultIndices.add(1000);
        defaultIndices.add(10000);
        defaultIndices.add(100000);
        defaultIndices.add(1000000);
        return defaultIndices;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Set<Integer> getIndices() {
        return indices;
    }

    public void setIndices(Set<Integer> indices) {
        this.indices = indices;
    }

    @Override
    public String getDescription() {
        return "An irrational decimal fraction is created by concatenating the positive integers:\n\n"
                + "0.123456789101112131415161718192021...\n\n"
                + "It can be seen that the 12th digit of the fractional part is 1.\n\n"
                + "If dn represents the nth digit of the fractional part,"
                + " find the value of the following expression.\n\n"
                + "d1 × d10 × d100 × d1000 × d10000 × d100000 × d1000000";
    }

    @Override
    public String run() {
        long product = 1;
        StringBuilder stringBuilder = new StringBuilder();

        int current = 1;
        while (stringBuilder.length() <= this.getMax()) {
            stringBuilder.append(current);
            if (this.getIndices().contains(current)) {
                product *= (stringBuilder.charAt(current - 1) - '0');
            }
            current++;
        }

        return String.valueOf(product);
    }
}
