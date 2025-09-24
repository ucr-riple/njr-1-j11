package ru.drunkard.movestrategies;

import ru.drunkard.field.GameField;
import ru.drunkard.utility.Point;

import java.util.List;
import java.util.Random;

public class DrunkardMoveStrategy implements IUndirectedMoveStrategy {

    public Point nextPosition(Point start, GameField field) {
        List<Point> positions = field.getAllNeighbours(start);
        Random rndGen = new Random();
        return positions.get(rndGen.nextInt(positions.size()));
    }
}
