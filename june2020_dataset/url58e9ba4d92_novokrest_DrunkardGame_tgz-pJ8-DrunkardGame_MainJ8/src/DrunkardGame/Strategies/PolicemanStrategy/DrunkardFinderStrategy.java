package DrunkardGame.Strategies.PolicemanStrategy;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.StaticObjects.GlassPoint;
import DrunkardGame.States.DrunkardStates.DrunkardLyingState;
import DrunkardGame.States.DrunkardStates.DrunkardSleepingState;
import DrunkardGame.Strategies.FinderStrategy;

/**
 * Created by Admin on 4/13/14.
 */
public class DrunkardFinderStrategy extends FinderStrategy {
    @Override
    public void visit(DrunkardLyingState drunkard, Field field) {
        targetObjectCoordinates = drunkard.getCoordinates();
    }

    @Override
    public void visit(DrunkardSleepingState drunkard, Field field) {
        targetObjectCoordinates = drunkard.getCoordinates();
    }

}
