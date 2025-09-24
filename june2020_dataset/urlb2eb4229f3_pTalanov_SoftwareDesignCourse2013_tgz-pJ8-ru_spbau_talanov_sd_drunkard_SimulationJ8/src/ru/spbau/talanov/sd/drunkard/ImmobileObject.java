package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public abstract class ImmobileObject implements BoardObject {
    @NotNull
    private final Position position;

    private final char representation;

    protected ImmobileObject(@NotNull Position position, char representation) {
        this.position = position;
        this.representation = representation;
    }

    @NotNull
    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public char representation() {
        return representation;
    }
}
