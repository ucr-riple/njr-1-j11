package ru.spbau.amanov.drunkard;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import ru.spbau.amanov.drunkard.GameObjects.Empty;
import ru.spbau.amanov.drunkard.GameObjects.GameObject;

/**
 * Created by karim on 6/4/14.
 */
public abstract class AbstractField {

    /**
     *  Class constructor.
     */
    public AbstractField() {
        WIDTH = GameConfig.FIELD_WIDTH;
        HEIGHT = GameConfig.FIELD_HEIGHT;
        field = new GameObject[HEIGHT][WIDTH];
        initField();
    }

    /**
     *  Visit all field objects.
     * @param visitor
     */
    public void visitFieldObjects(ICollisionVisitor visitor) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < getWidth(i); j++) {
                field[i][j].accept(visitor);
            }
        }
    }

    /**
     * Checks that the coordinate belongs to the field.
     */
    public boolean validatePosition(Position pos) {
        return (pos.getRow() >= 0 && pos.getColumn() >= 0 && pos.getRow() < HEIGHT && pos.getColumn() < getWidth(pos.getRow()));
    }


    /**
     *  Add object to the game field.
     * @param obj added object.
     * @param pos coordinate of the added object.
     */
    public void addObject(GameObject obj, Position pos) {
        field[pos.getRow()][pos.getColumn()] = obj;
    }

    /**
     *  Remove object from the game field.
     * @param coord object coordinate.
     */
    public void removeObject(Position pos) {
        addObject(new Empty(), pos);
    }

    /**
     *  Get object from the game field.
     * @param coord object coordinate.
     * @return corresponding object.
     */
    public GameObject getObject(Position pos) {
        return getObject(pos.getRow(), pos.getColumn());
    }

    /**
     *  Get object from the game field.
     * @param row
     * @param col
     * @return corresponding object.
     */
    public GameObject getObject(int row, int col) {
        return field[row][col];
    }


    /**
     *  Set all objects isVisited parameter to false.
     */
    public void initFieldObjects() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < getWidth(i); j++) {
                field[i][j].setVisited(false);
            }
        }
    }

    /**
     * Get possible steps from position.
     * @param from
     * @return
     */
    public List<Position> getPossibleSteps(Position from) {
        LinkedList<Position> positions = new LinkedList<Position>();
        positions.add(from.incColumn());
        positions.add(from.incRow());
        positions.add(from.decrColumn());
        positions.add(from.decrRow());
        for(Iterator<Position> it = positions.listIterator(); it.hasNext();) {
            if (!validatePosition(it.next())) {
                it.remove();
            }
        }
        return positions;
    }

    public int getHeight() {
        return HEIGHT;
    }

    private void initField() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < getWidth(i); j++) {
                addObject(new Empty(), new Position(i, j));
            }
        }
    }

    /**
     * Get width in row.
     * @param row
     * @return
     */
    public abstract int getWidth(int row);

    /**
     * Is row shift from begining of the field/
     * @param row
     * @return
     */
    public abstract boolean isShift(int row);

    protected final int WIDTH;
    protected final int HEIGHT;
    protected GameObject field[][];
}
