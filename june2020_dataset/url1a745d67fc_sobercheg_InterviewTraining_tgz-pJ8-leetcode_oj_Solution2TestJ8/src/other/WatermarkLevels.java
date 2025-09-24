package other;

import java.util.Arrays;

/**
 * Created by Sobercheg on 11/18/13.
 */
public class WatermarkLevels {

    public int[] getWaterHeights(int[] board) {
        int waterMark = 0;
        int[] heights = new int[board.length];

        for (int i = 0; i < board.length; i++) {
            if (board[i] > waterMark) {
                waterMark = board[i];
            }
            heights[i] = waterMark;
        }

        waterMark = 0;
        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i] > waterMark) {
                waterMark = board[i];
            }
            if (heights[i] > waterMark) {
                heights[i] = waterMark;
            }
        }

        return heights;
    }

    public static void main(String[] args) {
        int[] desk = new int[]{1, 2, 1, 2, 4, 3, 1, 2, 3, 5, 3, 1, 4, 1, 2};
        WatermarkLevels levels = new WatermarkLevels();
        System.out.println(Arrays.toString(levels.getWaterHeights(desk)));
    }
}
