package jswang.GoogleInterview.ShuffleArray;

import java.util.Random;

/**
 * Created by willwjs on 10/27/15.
 */
public class Solution {
    public void shuffle(int[] array) {
        int waitForSwap = array.length - 1;
        Random random = new Random();
        for (; waitForSwap > 0; waitForSwap--) {
            int randomIndex = random.nextInt(waitForSwap + 1);

            int tmp = array[randomIndex];
            array[randomIndex] = array[waitForSwap];
            array[waitForSwap] = tmp;
        }

        // Print
        for (int i : array) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        new Solution().shuffle(new int[] {1, 2, 3, 4, 5, 6});
    }
}