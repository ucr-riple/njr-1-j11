package other;

import java.math.BigInteger;

/**
 * Created by sobercheg on 12/2/13.
 * A one dimensional array represents pixels on a 80x80 display with 3 bits per pixel for color (RGB).
 * Write an algorithm to paint 8x8 checkboard cells.
 */
public class CheckboardPaint {
    private static final int CELL_NUMBER = 8;
    private static final int PIXELS_PER_CELL = 10;
    private static final int X_PIXELS = CELL_NUMBER * PIXELS_PER_CELL;
    private static final int Y_PIXELS = CELL_NUMBER * PIXELS_PER_CELL;
    private static final int COLOR_BITS = 3;

    public int[] getPaintedCells() {
        int[] pixelBytes = new int[(int) (X_PIXELS * Y_PIXELS * COLOR_BITS + 32) / 32];
        for (int yCell = 0; yCell < CELL_NUMBER; yCell++) {
            for (int xCell = 0; xCell < CELL_NUMBER; xCell++) {
                if ((xCell + yCell) % 2 == 0) {
                    fillCell(pixelBytes, xCell, yCell);
                }
            }
        }
        return pixelBytes;
    }

    public void fillCell(int[] pixelBytes, int xCell, int yCell) {
        for (int yPixel = yCell * PIXELS_PER_CELL; yPixel < yCell * PIXELS_PER_CELL + PIXELS_PER_CELL; yPixel++) {
            for (int xPixel = xCell * PIXELS_PER_CELL; xPixel < xCell * PIXELS_PER_CELL + PIXELS_PER_CELL; xPixel++) {
                fillPixel(pixelBytes, xPixel, yPixel);
            }
        }
    }

    public void fillPixel(int[] pixelBytes, int xPixel, int yPixel) {
        int bitIndex = (yPixel * X_PIXELS + xPixel) * COLOR_BITS;
        for (int i = bitIndex; i < bitIndex + COLOR_BITS; i++) {
            setBitValue(pixelBytes, i);
        }
    }

    public void setBitValue(int[] pixelBytes, int bitIndex) {
        int arrayIndex = bitIndex / 32;
        int bitIndexInByte = bitIndex % 32;
        int bitIntValue = 1 << (31 - bitIndexInByte);
        int value = pixelBytes[arrayIndex] | bitIntValue;
        pixelBytes[arrayIndex] = value;
    }


    public static void main(String[] args) {
        CheckboardPaint paint = new CheckboardPaint();
        int[] painted = paint.getPaintedCells();
        StringBuilder sb = new StringBuilder();

        for (int aPainted : painted) {
            sb.append(String.format("%032d", new BigInteger(Integer.toBinaryString(aPainted))));
        }

        for (int i = 0; i < sb.length(); i++) {
            if (i % (COLOR_BITS * Y_PIXELS) == 0) System.out.println();
            System.out.print(sb.charAt(i));
        }
    }
}
