package DrunkardGame.States.PolicemanStates;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.MovableObjects.Beggar;
import DrunkardGame.GameObjects.MovableObjects.Policeman;
import DrunkardGame.GameObjects.StaticObjects.Bottle;
import DrunkardGame.States.BeggarStates.BeggarGoingToGlassPointState;
import DrunkardGame.States.DrunkardStates.DrunkardLyingState;
import DrunkardGame.States.DrunkardStates.DrunkardSleepingState;
import DrunkardGame.Strategies.BeggarStrategies.BottleFinderStrategy;
import DrunkardGame.Strategies.PolicemanStrategy.DrunkardFinderStrategy;

/**
 * Created by novokrest on 4/16/14.
 */
public class GoingToDrunkardState extends PolicemanState {
    public GoingToDrunkardState(Policeman policeman) {
        super(policeman);
        strategy = new DrunkardFinderStrategy();
    }

    @Override
    public void visit(DrunkardLyingState drunkard, Field field) {
        field.unregister(drunkard.getCoordinates());
        field.swapGameObject(drunkard.getCoordinates(), policeman.getCoordinates());
        policeman.setState(new GoingToPoliceStationState(policeman));
    }

    @Override
    public void visit(DrunkardSleepingState drunkard, Field field) {
        field.unregister(drunkard.getCoordinates());
        field.swapGameObject(drunkard.getCoordinates(), policeman.getCoordinates());
        policeman.setState(new GoingToPoliceStationState(policeman));
    }
}
