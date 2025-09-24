package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public interface Movable extends BoardObject {
    void setPosition(@NotNull Position position);
}
