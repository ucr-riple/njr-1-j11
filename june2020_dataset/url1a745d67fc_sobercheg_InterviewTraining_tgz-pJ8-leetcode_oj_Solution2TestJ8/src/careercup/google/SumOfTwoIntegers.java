package careercup.google;

import java.util.Arrays;

/**
 * Created by Sobercheg on 12/16/13.
 * http://www.careercup.com/question?id=5678435150069760
 * <p/>
 * Given the array of digits (0 is also allowed), what is the minimal sum of two integers that are made of the digits contained in the array.
 * For example, array: 1 2 7 8 9. The min sum (129 + 78) should be 207
 */
public class SumOfTwoIntegers {

    public int minSum(int[] digits) {
        int[] sortedDigits = Arrays.copyOf(digits, digits.length);
        Arrays.sort(sortedDigits);

        // Observation 1: smaller digits should be most significant digits
        // Obesrvation 2: the number of digits in each number cannot differ by more than 1
        // Idea from the observations: start filling out number from right to left.
        // Next digit is appended to previous smaller one if the number of digits in both numbers is equal

        int num1 = 0;
        int num2 = 0;

        int num1Digits = 0;
        int num2Digits = 0;

        int previousNum = 0;

        for (int i = sortedDigits.length - 1; i >= 0; i--) {
            if (i == sortedDigits.length - 1 || num1Digits < num2Digits) {
                num1 = addDigitToLeft(num1, sortedDigits[i], num1Digits++);
                previousNum = num1;
            } else if (num1Digits > num2Digits) {
                num2 = addDigitToLeft(num2, sortedDigits[i], num2Digits++);
                previousNum = num2;
            } else { // num1Digits = num2Digits
                if (num1 == previousNum) num1 = addDigitToLeft(num1, sortedDigits[i], num1Digits++);
                else num2 = addDigitToLeft(num2, sortedDigits[i], num2Digits++);
            }
        }
        System.out.println(String.format("%d + %d = %d", num1, num2, num1 + num2));
        return num1 + num2;
    }

    int addDigitToLeft(int num, int digit, int numOfDigits) {
        return num + digit * (int) Math.pow(10, numOfDigits);
    }

    public static void main(String[] args) {
        SumOfTwoIntegers sumOfTwoIntegers = new SumOfTwoIntegers();
        sumOfTwoIntegers.minSum(new int[]{1, 2, 7, 8, 9, 3, 0, 0});
    }
}
