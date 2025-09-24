package DrunkardGame.Strategies.BeggarStrategies;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.StaticObjects.*;
import DrunkardGame.Strategies.FinderStrategy;

/**
 * Created by novokrest on 4/13/14.
 */
public class BottleFinderStrategy extends FinderStrategy {

    public BottleFinderStrategy() {
        super();
    }

    @Override
    public void visit(Bottle bottle, Field field) {
        targetObjectCoordinates = bottle.getCoordinates();
    }
}
