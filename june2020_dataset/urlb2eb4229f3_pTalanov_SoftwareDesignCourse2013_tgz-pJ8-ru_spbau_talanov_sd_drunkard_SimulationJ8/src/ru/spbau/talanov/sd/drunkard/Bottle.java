package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public final class Bottle extends ImmobileObject {
    public Bottle(@NotNull Position position) {
        super(position, 'B');
    }
}
