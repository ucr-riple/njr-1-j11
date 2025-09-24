package com.rmd.personal.projecteuler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class P050 implements Problem {

    private static final int DEFAULT_MAX = 1000000;

    private int max = DEFAULT_MAX;

    private Set<Long> primes = new HashSet<Long>();
    private List<Long> cumulativePrimes = new ArrayList<Long>();

    public P050() {
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    private Set<Long> getPrimes() {
        return primes;
    }

    private List<Long> getCumulativePrimes() {
        return cumulativePrimes;
    }

    @Override
    public String getDescription() {
        return "The prime 41, can be written as the sum of six consecutive primes:\n\n"
                + "41 = 2 + 3 + 5 + 7 + 11 + 13\n"
                + "This is the longest sum of consecutive primes that adds to a prime below one-hundred.\n\n"
                + "The longest sum of consecutive primes below one-thousand that adds to a prime, contains 21 terms, "
                + "and is equal to 953.\n\n"
                + "Which prime, below one-million, can be written as the sum of the most consecutive primes?";
    }

    @Override
    public String run() {
        this.populatePrimesCumulativeSumUpTo();

        return String.valueOf(this.findLargestPrime());
    }

    private void populatePrimesCumulativeSumUpTo() {
        this.getPrimes().add(0L);
        this.getCumulativePrimes().add(0L);
        boolean[] values = new boolean[this.getMax()];
        int index = 1;
        boolean stopCumulative = false;
        for (int i = 2; i < this.getMax(); i++) {
            if (!values[i]) {
                if (!stopCumulative) {
                    long previousCumulative = this.getCumulativePrimes().get(index - 1);
                    long cumulative = previousCumulative + i;
                    if (cumulative < this.getMax()) {
                        this.getCumulativePrimes().add(cumulative);
                    } else {
                        stopCumulative = true;
                    }
                }
                this.getPrimes().add((long) i);
                for (int j = i; j < this.getMax(); j += i) {
                    values[j] = true;
                }
                index++;
            }
        }
    }

    private long findLargestPrime() {
        long mostConsecutive = 0;
        long largestPrime = 0;
        for (int i = this.getCumulativePrimes().size() - 1; i >= 0; i--) {
            if (i < mostConsecutive) {
                return largestPrime;
            }
            long cumulativeUpper = this.getCumulativePrimes().get(i);
            for (int j = 0; j < i; j++) {
                long cumulativeLower = this.getCumulativePrimes().get(j);
                long possiblePrime = cumulativeUpper - cumulativeLower;
                if (this.getPrimes().contains(possiblePrime)) {
                    long consecutive = i - j;
                    if (consecutive > mostConsecutive) {
                        largestPrime = possiblePrime;
                        mostConsecutive = consecutive;
                        // Can't get a longer chain with this cumulative upper
                        // as we decrease the chain length each time.
                        break;
                    }
                }
            }
        }
        return largestPrime;
    }
}
