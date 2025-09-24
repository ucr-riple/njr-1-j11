package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public final class PoliceStation extends ImmobileObject {
    public PoliceStation(@NotNull Position position) {
        super(position, 'S');
    }
}
