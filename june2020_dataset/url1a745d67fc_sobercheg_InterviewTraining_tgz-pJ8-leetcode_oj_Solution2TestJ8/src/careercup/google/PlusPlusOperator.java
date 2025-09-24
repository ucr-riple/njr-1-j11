package careercup.google;

import java.util.Arrays;

/**
 * Created by Sobercheg on 11/22/13.
 */
public class PlusPlusOperator {

    public int[] increment(int[] arr) {
        int[] result = new int[arr.length + 1];
        int carryover = 1;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] + carryover > 9) {
                result[i + 1] = arr[i] + carryover - 10;
                carryover = 1;
            } else {
                result[i + 1] = arr[i] + carryover;
                carryover = 0;
            }
        }
        result[0] = carryover;

        return result;
    }

    public static void main(String[] args) {
        PlusPlusOperator plusPlusOperator = new PlusPlusOperator();
        System.out.println(Arrays.toString(plusPlusOperator.increment(new int[]{9, 9, 9, 9})));
    }
}
