package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public final class RecyclingPoint extends ImmobileObject {
    public RecyclingPoint(@NotNull Position position) {
        super(position, 'R');
    }
}
