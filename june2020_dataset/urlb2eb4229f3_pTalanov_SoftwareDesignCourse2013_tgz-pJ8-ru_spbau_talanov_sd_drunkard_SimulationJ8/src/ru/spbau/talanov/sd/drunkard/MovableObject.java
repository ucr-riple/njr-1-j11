package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public abstract class MovableObject implements Movable {

    public MovableObject(@NotNull Position position) {
        this.position = position;
    }

    @NotNull
    private Position position;

    @Override
    public void setPosition(@NotNull Position position) {
        this.position = position;
    }

    @NotNull
    @Override
    public Position getPosition() {
        return position;
    }
}
