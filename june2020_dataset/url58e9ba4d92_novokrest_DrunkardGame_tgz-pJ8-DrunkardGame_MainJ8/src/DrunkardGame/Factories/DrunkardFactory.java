package DrunkardGame.Factories;

import DrunkardGame.GameObjects.CommonObjects.GameMovingObject;
import DrunkardGame.GameObjects.MovableObjects.Drunkard;

/**
 * Created by novokrest on 4/12/14.
 */
public class DrunkardFactory extends MovingObjectFactory{
    public DrunkardFactory(int movingObjectX, int movingObjectY) {
        super(movingObjectX, movingObjectY);
    }

    @Override
    public GameMovingObject makeObject() {
        return new Drunkard(movingObjectX, movingObjectY);
    }
}
