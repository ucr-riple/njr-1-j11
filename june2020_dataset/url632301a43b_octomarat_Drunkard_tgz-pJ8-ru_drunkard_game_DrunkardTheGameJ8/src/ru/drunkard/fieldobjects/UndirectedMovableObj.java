package ru.drunkard.fieldobjects;

import ru.drunkard.movestrategies.IUndirectedMoveStrategy;
import ru.drunkard.utility.Point;

abstract public class UndirectedMovableObj extends MovableObj {
    protected IUndirectedMoveStrategy moveStrategy;

    protected UndirectedMovableObj(Point pos, IUndirectedMoveStrategy ms) {
        super(pos);
        moveStrategy = ms;
    }
}
