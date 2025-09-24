package DrunkardGame.Strategies.DrunkardStrategies;

import DrunkardGame.GameInterfaces.IGameStrategy;
import DrunkardGame.GameObjects.CommonObjects.Coordinates;
import DrunkardGame.GameObjects.CommonObjects.Field;

/**
 * Created by novokrest on 4/15/14.
 */
public class DrunkardWalkingStrategy implements IGameStrategy{
    @Override
    public Coordinates getOptimalStep(Coordinates drunkardCoordinates, Field field) {
        int oldX = drunkardCoordinates.getX();
        int oldY = drunkardCoordinates.getY();
        int newX = oldX, newY = oldY;
        double randomStep = Math.random();
        if (randomStep < 0.25) {
            newX -= 1;
        } else if (randomStep < 0.5) {
            newY -= 1;
        } else if (randomStep < 0.75) {
            newX += 1;
        } else {
            newY += 1;
        }
        return new Coordinates(newX, newY);
    }
}
