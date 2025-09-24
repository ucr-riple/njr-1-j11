package DrunkardGame.Factories;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameMovingObject;
import DrunkardGame.GameObjects.MovableObjects.Beggar;
import DrunkardGame.GameObjects.MovableObjects.Policeman;

/**
 * Created by novokrest on 4/16/14.
 */
public class BeggarFactory extends MovingObjectFactory{
    public BeggarFactory(int movingObjectX, int movingObjectY) {
        super(movingObjectX, movingObjectY);
    }

    @Override
    public GameMovingObject makeObject() {
        return new Beggar(movingObjectX, movingObjectY);
    }
}
