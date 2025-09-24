package DrunkardGame.States.BeggarStates;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.MovableObjects.Beggar;
import DrunkardGame.GameObjects.StaticObjects.*;
import DrunkardGame.Strategies.BeggarStrategies.BottleFinderStrategy;

/**
 * Created by Admin on 4/13/14.
 */
public class BeggarFindingBottleState extends BeggarState {

    public BeggarFindingBottleState(Beggar beggar) {
        super(beggar);
        beggarStrategy = new BottleFinderStrategy();
    }

    @Override
    public void visit(Bottle bottle, Field field) {
        field.unregister(bottle.getCoordinates());
        field.swapGameObject(beggar.getCoordinates(), bottle.getCoordinates());
        beggar.setState(new BeggarGoingToGlassPointState(beggar));
    }
}
