package com.rmd.personal.projecteuler;

public class P046 implements Problem {

    @Override
    public String getDescription() {
        return "It was proposed by Christian Goldbach that every odd composite number can be written as the sum of a "
                + "prime and twice a square.\n\n"
                + "9 = 7 + 2×12\n"
                + "15 = 7 + 2×22\n"
                + "21 = 3 + 2×32\n"
                + "25 = 7 + 2×32\n"
                + "27 = 19 + 2×22\n"
                + "33 = 31 + 2×12\n\n"
                + "It turns out that the conjecture was false.\n\n"
                + "What is the smallest odd composite that cannot be written as the sum of a prime and twice a square?";
    }

    @Override
    public String run() {
        long val = 1;

        boolean found = true;
        while (found) {
            val += 2;

            if (Common.getPrimesAsSet().contains(val)) {
                continue;
            }

            found = false;
            for (long prime : Common.getPrimes()) {
                if (found || prime >= val) {
                    break;
                }
                found = Common.isSquare((val - prime) / 2);
            }
        }

        return String.valueOf(val);
    }
}
