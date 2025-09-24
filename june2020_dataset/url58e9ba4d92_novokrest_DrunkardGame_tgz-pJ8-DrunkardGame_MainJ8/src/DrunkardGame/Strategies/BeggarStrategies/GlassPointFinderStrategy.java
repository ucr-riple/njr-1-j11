package DrunkardGame.Strategies.BeggarStrategies;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.StaticObjects.GlassPoint;
import DrunkardGame.Strategies.FinderStrategy;

/**
 * Created by Admin on 4/13/14.
 */
public class GlassPointFinderStrategy extends FinderStrategy {
    @Override
    public void visit(GlassPoint glassPoint, Field field) {
        targetObjectCoordinates = glassPoint.getCoordinates();
    }
}
