package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Pavel Talanov
 */
public abstract class BoardTopology {

    private static final Random RANDOM = new Random();

    public abstract List<Position> getAdjacentPositions(@NotNull Position position);

    @NotNull
    public Position getRandomAdjacentPosition(@NotNull Position position) {
        List<Position> adjacentPositions = getAdjacentPositions(position);
        return adjacentPositions.get(RANDOM.nextInt(adjacentPositions.size()));
    }

    @NotNull
    public Collection<Position> allPositionsInRadius(@NotNull Position center, int radius) {
        assert radius >= 0;
        Set<Position> result = new HashSet<>();
        result.add(center);
        for (int i = 0; i < radius; ++i) {
            Set<Position> current = new HashSet<>(result);
            for (Position position : result) {
                current.addAll(getAdjacentPositions(position));
            }
            result = current;
        }
        return result;
    }
}
