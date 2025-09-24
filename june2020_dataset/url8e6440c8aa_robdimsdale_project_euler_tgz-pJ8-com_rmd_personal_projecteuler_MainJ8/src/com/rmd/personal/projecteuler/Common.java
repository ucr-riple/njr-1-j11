package com.rmd.personal.projecteuler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Common {

    private static final int MAX_PRIME_VALUE = 10000000;

    private Common() {

    }

    private static List<Long> primes;
    private static Set<Long> primesAsSet;

    static {

        System.out.println("populating primes up to: " + MAX_PRIME_VALUE);
        Date startTime = new Date();
        primes = new ArrayList<Long>();
        primesAsSet = new HashSet<Long>();
        populatePrimesUpTo(MAX_PRIME_VALUE);
        Date endTime = new Date();
        System.out.println("done - took " + (endTime.getTime() - startTime.getTime()) + " ms.");
    }

    public static List<Long> getPrimes() {
        return primes;
    }

    public static Set<Long> getPrimesAsSet() {
        return primesAsSet;
    }

    private static void populatePrimesUpTo(int end) {
        boolean[] values = new boolean[end];
        for (int i = 2; i < end; i++) {
            if (!values[i]) {
                primes.add((long) i);
                primesAsSet.add((long) i);
                for (int j = i; j < end; j += i) {
                    values[j] = true;
                }
            }
        }
    }

    public static long sum(long n) {
        return (n * (n + 1)) / 2;
    }

    public static Map<Long, Integer> findPrimeFactorTreeForValue(long number) {
        Map<Long, Integer> factorTree = new HashMap<Long, Integer>();

        boolean isPrimeFactor;

        for (int i = 0; i < Common.getPrimes().size(); i++) {
            long currentPrime = Common.getPrimes().get(i);
            // If the current prime squared is greater than the current value then the current value is a prime.
            // The exponent of that prime is 1, and we can return as we know there are no more prime factors.
            if (currentPrime * currentPrime > number) {
                factorTree.put(currentPrime, 1);
                return factorTree;
            }

            int currentFactorCount = 0;

            isPrimeFactor = false;
            while (number % currentPrime == 0) {
                isPrimeFactor = true;
                number = number / Common.getPrimes().get(i);
                currentFactorCount++;
            }

            if (isPrimeFactor) {
                if (factorTree.containsKey(currentPrime)) {
                    factorTree.put(currentPrime, factorTree.get(currentPrime) + currentFactorCount);
                } else {
                    factorTree.put(currentPrime, currentFactorCount);
                }
            }

            // If there is no remainder, return the count
            if (number == 1) {
                return factorTree;
            }
        }
        return factorTree;
    }

    public static long sumDigitsInString(String numberAsString) {
        long sum = 0;
        for (int i = 0; i < numberAsString.length(); i++) {
            sum += Integer.valueOf(numberAsString.substring(i, i + 1));
        }
        return sum;
    }

    public static long factorial(long n) {
        if (n == 0) {
            return 1;
        }

        if (n < 0) {
            throw new IllegalArgumentException("Can only calculate factorial for non-negative n.");
        }

        final long maxInput = 39;
        if (n > maxInput) {
            throw new IllegalArgumentException("Can only calculate factorial for max input: " + maxInput + ".");
        }

        long result = 1;
        for (long i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static long sumOfFactorsPrime(int number) {
        long n = number;
        long sum = 1;
        long p = Common.getPrimes().get(0);
        long j;
        int i = 0;

        while (p * p <= n && n > 1 && i < Common.getPrimes().size()) {
            p = Common.getPrimes().get(i);
            i++;
            if (n % p == 0) {
                j = p * p;
                n = n / p;
                while (n % p == 0) {
                    j = j * p;
                    n = n / p;
                }
                sum = sum * (j - 1) / (p - 1);
            }
        }

        //A prime factor larger than the square root remains
        if (n > 1) {
            sum *= n + 1;
        }
        return sum - number;
    }
    public static boolean isPalindromeNumber(long number) {
        return Common.isPalindromeNumber(String.valueOf(number));
    }

    public static boolean isPalindromeNumber(String numberAsString) {

        int forwardCharIndex = 0;
        int backwardCharIndex = numberAsString.length() - 1;

        boolean keepGoing = true;
        while (keepGoing) {
            char forwardChar = numberAsString.charAt(forwardCharIndex);
            char backwardChar = numberAsString.charAt(backwardCharIndex);

            if (forwardChar != backwardChar) {
                return false;
            }

            if (numberAsString.length() % 2 == 0) {
                keepGoing = forwardCharIndex < backwardCharIndex - 1;
            } else {
                keepGoing = forwardCharIndex < backwardCharIndex;
            }

            forwardCharIndex++;
            backwardCharIndex--;
        }

        return true;
    }

    public static String getNumberAsBinaryString(long number) {
        StringBuilder binary = new StringBuilder();

        if (number == 0) {
            binary.append(0);
        }

        while (number > 0) {
            binary.append(number % 2);
            number /= 2;
        }

        return binary.toString();
    }

    public static long concatenateNumbers(long number1, long number2) {
        long concatenated = number1;

        if (number2 == 0) {
            return number1 * 10;
        }

        long tmpMultiplicand = number2;
        while (tmpMultiplicand > 0) {
            tmpMultiplicand /= 10;
            concatenated *= 10;
        }

        concatenated += number2;

        return concatenated;
    }

    public static boolean isPandigital(long number, int maxDigit) {
        boolean[] containedDigits = new boolean[maxDigit];

        while (number > 0) {
            int digit = (int) (number % 10);
            if (digit != 0) {
                if (containedDigits[digit - 1]) {
                    return false;
                }
                containedDigits[digit - 1] = true;
            }
            number /= 10;
        }

        for (boolean contained : containedDigits) {
            if (!contained) {
                return false;
            }
        }
        return true;
    }

    public static int findMaxDigit(long number) {
        int max = 0;
        while (number > 0) {
            long digit = number % 10;
            if (digit > max) {
                max = (int) digit;
            }
            number /= 10;
        }
        return max;
    }

    public static boolean isTriangle(long number) {
        return (-1 + Math.sqrt(1 + 8 * number)) % 2 == 0;
    }

    public static boolean isPentagonal(long number) {
        return (1 + Math.sqrt(1 + 24 * number)) % 6 == 0;
    }

    public static boolean isHexagonal(long number) {
        return (1 + Math.sqrt(1 + 8 * number)) % 4 == 0;
    }

    public static boolean isSquare(long number) {
        return Math.sqrt(number) % 1 == 0;
    }

    // See http://www.mathblog.dk/project-euler-24-millionth-lexicographic-permutation/
    public static int[] nextPermutation(int[] perm) {

        int[] copyAndReverse = Common.copyAndReverse(perm);

        int[] copyAndSort = Arrays.copyOf(perm, perm.length);
        Arrays.sort(copyAndSort);

        if (Arrays.equals(copyAndReverse, copyAndSort)) {
            return copyAndReverse;
        }

        int[] returnedPerm = Arrays.copyOf(perm, perm.length);

        int n = returnedPerm.length;
        int i = n - 1;
        while (returnedPerm[i - 1] >= returnedPerm[i]) {
            i = i - 1;
        }

        int j = n;
        while (returnedPerm[j - 1] <= returnedPerm[i - 1]) {
            j = j - 1;
        }

        // swap values at position i-1 and j-1
        Common.swap(returnedPerm, i - 1, j - 1);

        i++;
        j = n;
        while (i < j) {
            Common.swap(returnedPerm, i - 1, j - 1);
            i++;
            j--;
        }
        return returnedPerm;
    }

    private static int[] copyAndReverse(int[] array) {
        int[] copy = Arrays.copyOf(array, array.length);
        for (int i = 0; i < array.length; i++) {
            copy[array.length - 1 - i] = array[i];
        }
        return copy;
    }

    private static void swap(int[] array, int i, int j) {
        int k = array[i];
        array[i] = array[j];
        array[j] = k;
    }

    public static long getLongFromDigitArray(int[] digits) {
        long value = 0;
        for (int digit : digits) {
            value *= 10;
            value += digit;
        }
        return value;
    }

    public static int[] getDigitArrayFromLong(long value) {
        if (value == 0) {
            return new int[]{0};
        }

        List<Integer> digitList = new ArrayList<Integer>();

        while (value > 0) {
            digitList.add((int) value % 10);
            value /= 10;
        }

        Collections.reverse(digitList);

        int[] digits = new int[digitList.size()];

        for (int i = 0; i < digitList.size(); i++) {
            digits[i] = digitList.get(i);
        }

        return digits;
    }
}