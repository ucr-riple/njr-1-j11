package ru.spbau.amanov.drunkard.GameObjects;
import ru.spbau.amanov.drunkard.*;

import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

/**
 * Class provides game object lantern.
 *
 * @author  Karim Amanov
 */
public class Lantern extends GameObject {

    /**
     * Class constructor.
     * @param f game field.
     * @param pos init position.
     * @param lightR light radius.
     */
    public Lantern(AbstractField f, Position pos, int lightR) {
        field = f;
        position = pos;
        lightRadius = lightR;
    }

    /**
     * Get all alight objects.
     * @return
     */
    public List<GameObject> getAlightObjects() {
        LinkedList<GameObject> ls = new LinkedList<GameObject>();
        int rowMin = Math.max(0, position.getRow() - lightRadius);
        int rowMax = Math.min(GameConfig.FIELD_HEIGHT, position.getRow() + lightRadius + 1);
        int colMin = Math.max(0, position.getColumn() - lightRadius);


        for (int i = rowMin; i < rowMax; i++) {
            int colMax = Math.min(field.getWidth(i), position.getColumn() + lightRadius + 1);
            for (int j = colMax - 1; j >= colMin; j--) {
                ls.add(field.getObject(i, j));
            }
        }
        return ls;
    }

    @Override
    public void accept(ICollisionVisitor visitor) {
        visitor.collisionWith(this);
    }

    AbstractField field;
    private final int lightRadius;
}
