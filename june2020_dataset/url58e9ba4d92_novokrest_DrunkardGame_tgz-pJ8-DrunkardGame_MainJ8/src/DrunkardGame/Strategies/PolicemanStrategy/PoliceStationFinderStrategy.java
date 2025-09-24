package DrunkardGame.Strategies.PolicemanStrategy;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.StaticObjects.GlassPoint;
import DrunkardGame.GameObjects.StaticObjects.PoliceStation;
import DrunkardGame.Strategies.FinderStrategy;

/**
 * Created by novokrest on 4/16/14.
 */
public class PoliceStationFinderStrategy extends FinderStrategy {
    @Override
    public void visit(PoliceStation policeStation, Field field) {
        targetObjectCoordinates = policeStation.getCoordinates();
    }
}
