package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public interface BoardObject {

    @NotNull
    Position getPosition();

    char representation();
}
