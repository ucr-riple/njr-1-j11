package ru.drunkard.movestrategies;

import ru.drunkard.field.GameField;
import ru.drunkard.utility.Point;

public interface IUndirectedMoveStrategy {
    public Point nextPosition(Point p, GameField field);
}
