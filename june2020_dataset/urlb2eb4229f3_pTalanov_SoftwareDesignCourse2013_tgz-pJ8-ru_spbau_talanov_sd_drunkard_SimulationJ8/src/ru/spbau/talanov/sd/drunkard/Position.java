package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public final class Position {

    private final int x;

    private final int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    @NotNull
    public static Position at(int x, int y) {
        return new Position(x, y);
    }
}
