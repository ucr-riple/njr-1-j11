package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static ru.spbau.talanov.sd.drunkard.Position.at;

/**
 * @author Pavel Talanov
 */
public final class HexagonalTopology extends BoardTopology {
    @Override
    public List<Position> getAdjacentPositions(@NotNull Position position) {
        int x = position.getX();
        int y = position.getY();
        boolean evenRow = y % 2 == 0;
        int dx = evenRow ? 0 : 1;
        return Arrays.asList(at(x - 1, y), at(x + 1, y), at(x - 1 + dx, y - 1),
                at(x + dx, y - 1), at(x - 1 + dx, y + 1), at(x + dx, y + 1));
    }
}
