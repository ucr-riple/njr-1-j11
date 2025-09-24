package ru.drunkard.fieldobjects;

import ru.drunkard.field.GameField;
import ru.drunkard.movestrategies.IDirectedMoveStrategy;
import ru.drunkard.utility.Point;

import java.util.Iterator;
import java.util.List;

abstract public class DirectedMovableObj extends MovableObj {
    protected IDirectedMoveStrategy moveStrategy;
    protected Point searchAreaStart;
    protected Point searchAreaEnd;
    protected boolean hasTarget = false;

    protected DirectedMovableObj(Point pos, IDirectedMoveStrategy ms, Point searchAreaStart, Point searchAreaEnd) {
        super(pos);
        moveStrategy = ms;
        this.searchAreaStart = searchAreaStart;
        this.searchAreaEnd = searchAreaEnd;
    }

    protected Point tryFindTarget(GameField field) {
        Iterator<Point> iter = field.getIterator(searchAreaStart, searchAreaEnd);
        while(iter.hasNext()) {
            Point current = iter.next();
            field.sendVisitorToSector(current.x, current.y, this);
            if(hasTarget) {
                return current;
            }
        }
        return null;
    }

    protected boolean isNeighbourToStartPos(Point startPos, GameField field) {
        List<Point> startPointNeighbours = field.getAllNeighbours(startPos);
        for(Point n : startPointNeighbours) {
            if(n.equals(pos)) {
                return true;
            }
        }
        return false;
    }
}
