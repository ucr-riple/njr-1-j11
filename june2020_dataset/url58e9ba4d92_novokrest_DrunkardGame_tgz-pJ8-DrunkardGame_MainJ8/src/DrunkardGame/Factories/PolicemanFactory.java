package DrunkardGame.Factories;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameMovingObject;
import DrunkardGame.GameObjects.MovableObjects.Policeman;

/**
 * Created by novokrest on 4/16/14.
 */
public class PolicemanFactory extends MovingObjectFactory {
    public PolicemanFactory(int movingObjectX, int movingObjectY) {
        super(movingObjectX, movingObjectY);
    }

    @Override
    public GameMovingObject makeObject() {
        return new Policeman(movingObjectX, movingObjectY);
    }
}
