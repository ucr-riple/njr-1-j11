package ru.spbau.amanov.drunkard;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by karim on 6/4/14.
 */
public class HexagonalField extends AbstractField {

    @Override
    public List<Position> getPossibleSteps(Position from) {
        List<Position> positions = super.getPossibleSteps(from);
        if (from.getRow() % 2 == 0) {
            positions.add(from.incRow().incColumn());
            positions.add(from.decrRow().incColumn());
        } else {
            positions.add(from.incRow().decrColumn());
            positions.add(from.decrRow().decrColumn());
        }
        for(Iterator<Position> it = positions.listIterator(); it.hasNext();) {
            if (!validatePosition(it.next())) {
                it.remove();
            }
        }

        return positions;
    }

    @Override
    public int getWidth(int row) {
        return (row % 2 == 0) ? WIDTH - 1 : WIDTH;
    }

    @Override
    public boolean isShift(int row) {
        return row % 2 == 0;
    }
}
