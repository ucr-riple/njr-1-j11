package ru.spbau.amanov.drunkard;

/**
 *  Class Position stores coordinates of the object on the game field.
 *
 *  @author  Karim Amanov
 */
public class Position {

    /**
     * Class constructor.
     * @param vx horizontal coordinate.
     * @param vy vertical coordinate.
     */
    public Position(int i, int j) {
        row = i;
        column = j;
    }

    /**
     *  Copy constructor.
     * @param coord Source coodinate object.
     */
    public Position(Position pos) {
        row = pos.row;
        column = pos.column;
    }

    /**
     * @return row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return column.
     */
    public int getColumn() {
        return column;
    }

    /**
     *  Increment row.
     */
    public Position incRow() {
        return new Position(row + 1, column);
    }

    /**
     *  Increment column.
     */
    public Position incColumn() {
        return new Position(row, column + 1);
    }

    /**
     *  Decrement row.
     */
    public Position decrRow() {
        return new Position(row - 1, column);
    }

    /**
     *  Decrement column.
     */
    public Position decrColumn() {
        return new Position(row, column - 1);
    }


    public boolean equals(Position pos) {
        return (row == pos.row && column == pos.column);
    }


    private int row;
    private int column;
}
