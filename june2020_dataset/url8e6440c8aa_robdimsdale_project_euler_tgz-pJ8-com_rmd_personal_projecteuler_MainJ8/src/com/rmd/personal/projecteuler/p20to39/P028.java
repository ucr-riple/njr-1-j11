package com.rmd.personal.projecteuler.p20to39; // SUPPRESS CHECKSTYLE fileLength

import com.rmd.personal.projecteuler.Problem;

public class P028 implements Problem {

    private static final int DEFAULT_MAX_A = 1001;

    private int squareSize;

    private long[][] numberSquare;

    public P028() {
        this.setSquareSize(DEFAULT_MAX_A);
    }

    public int getSquareSize() {
        return squareSize;
    }

    public void setSquareSize(int squareSize) {
        this.squareSize = squareSize;
    }

    private long[][] getNumberSquare() {
        return numberSquare;
    }

    @Override
    public String getDescription() {
        return "Starting with the number 1 and moving to the right in a clockwise direction "
                + "a 5 by 5 spiral is formed as follows:\n\n"
                + "21 22 23 24 25\n"
                + "20  7  8  9 10\n"
                + "19  6  1  2 11\n"
                + "18  5  4  3 12\n"
                + "17 16 15 14 13\n\n"
                + "It can be verified that the sum of the numbers on the diagonals is 101.\n\n"
                + "What is the sum of the numbers on the diagonals in a 1001 by 1001 spiral formed in the same way?";
    }

    @Override
    public String run() {
        this.initializeNumberSquare();

        long sum = 0;
        for (int i = 0; i < this.getSquareSize(); i++) {
            sum += this.getNumberSquare()[i][i];
            sum += this.getNumberSquare()[this.getSquareSize() - 1 - i][i];
        }
        sum -= 1; // Remove the double-counted '1' in the middle.

        return String.valueOf(sum);
    }

    private void initializeNumberSquare() {
        this.numberSquare = new long[this.getSquareSize()][this.getSquareSize()];

        Direction direction = Direction.LEFT;
        int i = this.getSquareSize() - 1;
        int j = 0;
        for (long currentValue = this.getSquareSize() * this.getSquareSize(); currentValue > 0; currentValue--) {
            this.getNumberSquare()[i][j] = currentValue;
            switch (direction) {
                case LEFT:
                    if (i == 0 || this.getNumberSquare()[i - 1][j] != 0) {
                        direction = Direction.DOWN;
                        j++;
                    } else {
                        i--;
                    }
                    break;
                case DOWN:
                    if (j == this.getSquareSize() - 1 || this.getNumberSquare()[i][j + 1] != 0) {
                        direction = Direction.RIGHT;
                        i++;
                    } else {
                        j++;
                    }
                    break;
                case RIGHT:
                    if (i == this.getSquareSize() - 1 || this.getNumberSquare()[i + 1][j] != 0) {
                        direction = Direction.UP;
                        j--;
                    } else {
                        i++;
                    }
                    break;
                case UP:
                    if (j == 0 || this.getNumberSquare()[i][j - 1] != 0) {
                        direction = Direction.LEFT;
                        i--;
                    } else {
                        j--;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized direction: " + direction);
            }
        }
    }

    private static enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
