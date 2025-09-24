package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static ru.spbau.talanov.sd.drunkard.Position.at;

/**
 * @author Pavel Talanov
 */
public final class RectangularTopology extends BoardTopology {
    @Override
    public List<Position> getAdjacentPositions(@NotNull Position position) {
        int x = position.getX();
        int y = position.getY();
        return Arrays.asList(at(x, y + 1), at(x - 1, y), at(x, y - 1), at(x + 1, y));
    }
}
