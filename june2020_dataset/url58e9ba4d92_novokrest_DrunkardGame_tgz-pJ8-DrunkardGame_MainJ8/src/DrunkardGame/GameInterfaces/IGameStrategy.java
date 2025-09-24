package DrunkardGame.GameInterfaces;

import DrunkardGame.GameObjects.CommonObjects.Coordinates;
import DrunkardGame.GameObjects.CommonObjects.Field;

/**
 * Created by novokrest on 4/12/14.
 */
public interface IGameStrategy {
    Coordinates getOptimalStep(Coordinates oldCoordinates, Field field);
}
