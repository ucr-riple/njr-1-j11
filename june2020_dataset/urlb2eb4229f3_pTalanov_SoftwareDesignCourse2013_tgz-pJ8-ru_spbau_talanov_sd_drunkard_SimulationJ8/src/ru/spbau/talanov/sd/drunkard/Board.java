package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Talanov
 */
public final class Board {

    private static final char EMPTY_CELL_CHAR = '.';
    private static final char NOTHING_CHAR = ' ';

    @NotNull
    private final BoardTopology topology;

    public Board(int size, @NotNull BoardTopology topology) {
        this.topology = topology;
        this.size = size;
    }

    private final int size;

    @NotNull
    private final Map<Position, BoardObject> objects = new HashMap<>();

    public void addObject(@NotNull BoardObject object) {
        assert isValid(object.getPosition());
        addSpecialObject(object);
    }

    public void addSpecialObject(@NotNull BoardObject object) {
        BoardObject existing = objects.put(object.getPosition(), object);
        if (existing != null) {
            throw new IllegalStateException("Adding object " + object.representation() + " at position "
                    + object.getPosition() + "but object " + existing.representation() + " is already there!");
        }
    }

    public void move(@NotNull Movable objectToMove, @NotNull Position whereTo) {
        assert isEmpty(whereTo) && isValid(whereTo);
        setEmpty(objectToMove.getPosition());
        objectToMove.setPosition(whereTo);
        addObject(objectToMove);
    }

    public void setEmpty(@NotNull Position position) {
        //noinspection ConstantConditions
        objects.put(position, null);
    }

    @NotNull
    public BoardObject getObject(@NotNull Position position) {
        BoardObject object = objects.get(position);
        if (object == null) {
            throw new IllegalStateException("Board at " + position + " is empty.");
        }
        return object;
    }

    public boolean isValid(@NotNull Position position) {
        return position.getX() >= getLeft() && position.getX() <= getRight() && position.getY() >= getBottom() && position.getY() <= getTop();
    }

    @NotNull
    public BoardTopology getTopology() {
        return topology;
    }

    @NotNull
    public String representation() {
        StringBuilder sb = new StringBuilder();
        for (int y = getBottom() - 1; y <= getTop(); ++y) {
            for (int x = getLeft() - 1; x <= getRight() + 1; ++x) {
                sb.append(representationAt(Position.at(x, y)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private char representationAt(@NotNull Position position) {
        if (!isEmpty(position)) {
            return getObject(position).representation();
        }
        if (!isValid(position)) {
            return NOTHING_CHAR;
        } else {
            assert isEmpty(position);
            return EMPTY_CELL_CHAR;
        }
    }

    public boolean isEmpty(@NotNull Position position) {
        return objects.get(position) == null;
    }

    private int getTop() {
        return size - 1;
    }

    private int getBottom() {
        return 0;
    }

    private int getRight() {
        return size - 1;
    }

    private int getLeft() {
        return 0;
    }
}
