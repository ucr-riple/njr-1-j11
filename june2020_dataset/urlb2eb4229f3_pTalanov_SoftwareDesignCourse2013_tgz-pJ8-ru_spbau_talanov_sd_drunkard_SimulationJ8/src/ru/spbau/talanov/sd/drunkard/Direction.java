package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public enum Direction {
    DOWN(0, -1),
    UP(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int deltaX;
    private final int deltaY;

    private Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    @NotNull
    public static Direction randomDirection() {
        return Direction.values()[RANDOM.nextInt(Direction.values().length)];
    }

    @NotNull
    private static final Random RANDOM = new Random();
}