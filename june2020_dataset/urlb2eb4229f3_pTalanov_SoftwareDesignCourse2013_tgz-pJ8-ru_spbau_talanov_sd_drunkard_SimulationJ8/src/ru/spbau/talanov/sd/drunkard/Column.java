package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public final class Column extends ImmobileObject {
    protected Column(@NotNull Position position) {
        super(position, 'C');
    }
}
