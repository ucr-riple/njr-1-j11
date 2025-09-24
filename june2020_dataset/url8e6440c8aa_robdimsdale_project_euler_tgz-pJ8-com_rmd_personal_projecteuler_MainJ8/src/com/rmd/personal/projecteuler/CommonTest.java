package com.rmd.personal.projecteuler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommonTest {

    @Test
    public void constructorIsPrivate() throws Exception {
        // Act
        Constructor<?>[] constructors = Common.class.getDeclaredConstructors();

        // Assert
        assertEquals(1, constructors.length);
        assertTrue(Modifier.isPrivate(constructors[0].getModifiers()));
    }

    @Test
    public void findFactorTreeReturnsCorrectlyFor2DigitNumber() {
        // Arrange
        final long value = 12L;

        // Act
        Map<Long, Integer> primeFactorTree = Common.findPrimeFactorTreeForValue(value);

        // Assert
        assertEquals(2, primeFactorTree.size());
        assertEquals((Integer) 2, primeFactorTree.get(2L));
        assertEquals((Integer) 1, primeFactorTree.get(3L));
    }

    @Test
    public void findFactorTreeReturnsCorrectlyFor3DigitNumber() {
        // Arrange
        final long value = 420;

        // Act
        Map<Long, Integer> primeFactorTree = Common.findPrimeFactorTreeForValue(value);

        // Assert
        assertEquals(4, primeFactorTree.size());
        assertEquals((Integer) 2, primeFactorTree.get(2L));
        assertEquals((Integer) 1, primeFactorTree.get(3L));
        assertEquals((Integer) 1, primeFactorTree.get(5L));
        assertEquals((Integer) 1, primeFactorTree.get(7L));
    }

    @Test
    @Ignore
    public void findFactorTreeReturnsCorrectlyFor7DigitNumber() {
        // Arrange
        final long value = 8004150L;

        // Act
        Map<Long, Integer> primeFactorTree = Common.findPrimeFactorTreeForValue(value);

        // Assert
        assertEquals(5, primeFactorTree.size());
        assertEquals((Integer) 1, primeFactorTree.get(2L));
        assertEquals((Integer) 3, primeFactorTree.get(3L));
        assertEquals((Integer) 2, primeFactorTree.get(5L));
        assertEquals((Integer) 2, primeFactorTree.get(7L));
        assertEquals((Integer) 2, primeFactorTree.get(11L));
    }

    @Test
    @Ignore
    public void findFactorTreeReturnsCorrectlyForLargePrime() {
        // Arrange
        final int powerOf2 = 3;
        final int powerOf3 = 5;
        final int powerOf17 = 2;
        final int powerOf19 = 2;
        final long value = (long)
                (Math.pow(2, powerOf2) * Math.pow(3, powerOf3) * Math.pow(17, powerOf17) * Math.pow(19, powerOf19));

        // Act
        Map<Long, Integer> primeFactorTree = Common.findPrimeFactorTreeForValue(value);

        // Assert
        assertEquals(4, primeFactorTree.size());
        assertEquals((Integer) powerOf2, primeFactorTree.get(2L));
        assertEquals((Integer) powerOf3, primeFactorTree.get(3L));
        assertEquals((Integer) powerOf17, primeFactorTree.get(17L));
        assertEquals((Integer) powerOf19, primeFactorTree.get(19L));
    }

    @Test
    public void factorialReturnsCorrectlyFor0() {
        // Arrange
        final long n = 0;

        // Act
        long actualResult = Common.factorial(n);

        // Assert
        final long expectedResult = 1;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void factorialReturnsCorrectlyFor1() {
        // Arrange
        final long n = 1;

        // Act
        long actualResult = Common.factorial(n);

        // Assert
        final long expectedResult = 1;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void factorialReturnsCorrectlyFor2() {
        // Arrange
        final long n = 2;

        // Act
        long actualResult = Common.factorial(n);

        // Assert
        final long expectedResult = 2;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void factorialReturnsCorrectlyFor3() {
        // Arrange
        final long n = 3;

        // Act
        long actualResult = Common.factorial(n);

        // Assert
        final long expectedResult = 6;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void factorialReturnsCorrectlyFor39() {
        // Arrange
        final long n = 39;

        // Act
        long actualResult = Common.factorial(n);

        // Assert
        final long expectedResult = 2304077777655037952L;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void factorialThrowsExceptionForGreaterThan39() {
        // Arrange
        final long n = -1;

        // Act & Assert
        boolean caughtCorrectException = false;
        try {
            Common.factorial(n);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("non-negative"));
            caughtCorrectException = true;
        }

        assertTrue(caughtCorrectException);
    }

    @Test
    public void factorialThrowsExceptionForLessThan0() {
        // Arrange
        final long n = 40;

        // Act & Assert
        boolean caughtCorrectException = false;
        try {
            Common.factorial(n);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("39"));
            caughtCorrectException = true;
        }

        assertTrue(caughtCorrectException);
    }

    @Test
    public void isPandigitalReturnsTrueForValid1to9PandigitalNumber() {
        // Arrange
        final long number = 987654321L;

        // Act & assert
        final int maxDigit = 9;
        assertTrue(Common.isPandigital(number, maxDigit));
    }

    @Test
    public void isPandigitalReturnsTrueForValid0to9PandigitalNumberIgnoring0() {
        // Arrange
        final long number = 9876543210L;

        // Act & assert
        final int maxDigit = 9;
        assertTrue(Common.isPandigital(number, maxDigit));
    }

    @Test
    public void isPandigitalReturnsFalseFor23467() {
        // Arrange
        final long number = 234567;

        // Act & assert
        final int maxDigit = 7;
        assertFalse(Common.isPandigital(number, maxDigit));
    }

    @Test
    public void isPandigitalReturnsFalseFor1123() {
        // Arrange
        final long number = 1123;

        // Act & assert
        final int maxDigit = 3;
        assertFalse(Common.isPandigital(number, maxDigit));
    }

    @Test
    public void concatenateNumbersReturnsCorrectlyForValidNumbers() {
        // Arrange
        final long number1 = 1234;
        final long number2 = 56789;

        // Act
        long returned = Common.concatenateNumbers(number1, number2);

        // Assert
        final long expected = 123456789L;
        assertEquals(expected, returned);
    }

    @Test
    public void isPalindromeNumberReturnsTrueForEvenLengthPalindrome() {
        // Act
        final long palindromeNumber = 123321;

        // Act & Assert
        assertTrue(Common.isPalindromeNumber(palindromeNumber));
    }

    @Test
    public void isPalindromeNumberReturnsTrueForOddLengthPalindrome() {
        // Act
        final long palindromeNumber = 12321;

        // Act & Assert
        assertTrue(Common.isPalindromeNumber(palindromeNumber));
    }

    @Test
    public void isPalindromeNumberReturnsFalseForNonPalindrome() {
        // Act
        final long palindromeNumber = 123421;

        // Act & Assert
        assertFalse(Common.isPalindromeNumber(palindromeNumber));
    }

    @Test
    public void getNumberAsBinaryStringReturnsCorrectlyForZero() {
        // Act
        final long number = 0;

        // Act
        String returned = Common.getNumberAsBinaryString(number);

        // Assert
        final String expected = "0";
        assertEquals(expected, returned);
    }

    @Test
    public void getNumberAsBinaryStringReturnsCorrectlyForOne() {
        // Act
        final long number = 1;

        // Act
        String returned = Common.getNumberAsBinaryString(number);

        // Assert
        final String expected = "1";
        assertEquals(expected, returned);
    }

    @Test
    public void getNumberAsBinaryStringReturnsCorrectlyFor585() {
        // Act
        final long number = 585;

        // Act
        String returned = Common.getNumberAsBinaryString(number);

        // Assert
        final String expected = "1001001001";
        assertEquals(expected, returned);
    }

    @Test
    public void getNumberAsBinaryStringReturnsCorrectlyFor9999999999() {
        // Act
        final long number = 9999999999L;

        // Act
        String returned = Common.getNumberAsBinaryString(number);

        // Assert
        final String expected = "1111111111000111110100000010101001";
        assertEquals(expected, returned);
    }

    @Test
    public void isTriangleReturnsTrueForValidTriangleNumber() {
        // Arrange
        final long value = 15;
        // Act & Assert
        assertTrue(Common.isTriangle(value));
    }

    @Test
    public void isTriangleReturnsFalseForInvalidTriangleNumber() {
        // Arrange
        final long value = 16;
        // Act & Assert
        assertFalse(Common.isTriangle(value));
    }

    @Test
    public void isPentagonalReturnsTrueForValidPentagonalNumber() {
        // Arrange
        final long value = 35;
        // Act & Assert
        assertTrue(Common.isPentagonal(value));
    }

    @Test
    public void isPentagonalReturnsFalseForInvalidHexagonalNumber() {
        // Arrange
        final long value = 36;
        // Act & Assert
        assertFalse(Common.isPentagonal(value));
    }

    @Test
    public void isHexagonalReturnsTrueForValidHexagonalNumber() {
        // Arrange
        final long value = 45;
        // Act & Assert
        assertTrue(Common.isHexagonal(value));
    }

    @Test
    public void isHexagonalReturnsFalseForInvalidPentagonalNumber() {
        // Arrange
        final long value = 46;
        // Act & Assert
        assertFalse(Common.isHexagonal(value));
    }

    @Test
    public void isSquareReturnsTrueForValidSquareNumber() {
        // Arrange
        final long value = 36;
        // Act & Assert
        assertTrue(Common.isSquare(value));
    }

    @Test
    public void isSquareReturnsFalseForInvalidSquareNumber() {
        // Arrange
        final long value = 37;
        // Act & Assert
        assertFalse(Common.isSquare(value));
    }

    @Test
    public void getLongFromDigitArrayReturnsCorrectlyForValidDigitArray() {
        // Arrange
        final int[] digits = {1, 2, 3, 4};

        // Act
        long returned = Long.valueOf(Common.getLongFromDigitArray(digits));

        // Assert
        final long expected = 1234;
        assertEquals(expected, returned);
    }

    @Test
    public void nextPermRotatesCorrectly() {
        // Arrange
        final int[] perm = {4, 3, 2, 1};

        // Act
        int[] returned = Common.nextPermutation(perm);

        // Assert
        final int[] expected = {1, 2, 3, 4};
        assertTrue(Arrays.equals(expected, returned));
    }

    @Test
    public void nextPermGeneratesNextCorrectly() {
        // Arrange
        final int[] perm = {1, 2, 4, 3};

        // Act
        int[] returned = Common.nextPermutation(perm);

        // Assert
        final int[] expected = {1, 3, 2, 4};
        assertTrue(Arrays.equals(expected, returned));
    }

    @Test
    public void getDigitArrayFromLongReturnsCorrectlyForZero() {
        // Arrange
        final long value = 0;

        // Act
        int[] returned = Common.getDigitArrayFromLong(value);

        // Assert
        final int[] expected = {0};
        assertTrue(Arrays.equals(expected, returned));
    }

    @Test
    public void getDigitArrayFromLongReturnsCorrectlyForNonZero() {
        // Arrange
        final long value = 1245;

        // Act
        int[] returned = Common.getDigitArrayFromLong(value);

        // Assert
        final int[] expected = {1, 2, 4, 5};
        assertTrue(Arrays.equals(expected, returned));
    }
}
