package DrunkardGame.GameInterfaces;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameMovingObject;
import DrunkardGame.GameObjects.CommonObjects.GameObject;

/**
 * Created by novokrest on 4/12/14.
 */
public interface IGameFactory {
    GameMovingObject getObject(Field field);
}
