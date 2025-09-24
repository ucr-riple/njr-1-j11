package DrunkardGame.GameObjects.StaticObjects;

import DrunkardGame.GameInterfaces.IGameVisitor;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameObject;

/**
 * Created by novokrest on 3/3/14.
 */
public class Pub extends GameObject {
    public Pub(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
    }

    @Override
    public void accept(IGameVisitor visitor, Field field) {
        visitor.visit(this, field);
    }

    @Override
    public void print() {
        System.out.print('T');
    }
}