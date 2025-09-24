package ru.drunkard.movestrategies;

import ru.drunkard.field.GameField;
import ru.drunkard.utility.Point;

public interface IDirectedMoveStrategy {
    public Point nextPosition(Point start, Point target, GameField field);
}
