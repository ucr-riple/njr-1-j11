package DrunkardGame.GameObjects.StaticObjects;

import DrunkardGame.GameInterfaces.IGameVisitor;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameObject;

/**
 * Created by novokrest on 4/12/14.
 */
public class Lamppost extends GameObject {
    final int lightingRadius = 3;

    public Lamppost(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
    }

    public int getLightingRadius() { return lightingRadius; }

    @Override
    public void accept(IGameVisitor visitor, Field field) {
        visitor.visit(this, field);
    }

    @Override
    public void print() {
        System.out.print('L');
    }
}
