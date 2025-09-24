package ru.drunkard.fieldobjects;

import ru.drunkard.field.GameField;
import ru.drunkard.utility.Point;

abstract public class MovableObj implements IFieldObj {
    protected Point pos;

    protected MovableObj(Point pos) { this.pos = pos; }

    public void moveInSector(Point destinationPos, GameField field) {
        if(!field.pointIsOutOfBorders(pos)) {
            field.setObjectInSector(pos.x, pos.y, null);
        }
        field.setObjectInSector(destinationPos.x, destinationPos.y, this);
        pos = destinationPos;
    }
}
