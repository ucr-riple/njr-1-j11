package DrunkardGame.GameObjects.CommonObjects;

import DrunkardGame.GameInterfaces.IGameMoving;

/**
 * Created by novokrest on 4/16/14.
 */
public class GameMovingObject extends GameObject implements IGameMoving {

    public GameMovingObject(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
    }

    @Override
    public void makeStep(Field field) {

    }
}
