package DrunkardGame.States.PolicemanStates;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.MovableObjects.Policeman;
import DrunkardGame.GameObjects.StaticObjects.PoliceStation;
import DrunkardGame.States.DrunkardStates.DrunkardLyingState;
import DrunkardGame.Strategies.PolicemanStrategy.PoliceStationFinderStrategy;

/**
 * Created by novokrest on 4/16/14.
 */
public class GoingToPoliceStationState extends PolicemanState {
    public GoingToPoliceStationState(Policeman policeman) {
        super(policeman);
        strategy = new PoliceStationFinderStrategy();
    }

    @Override
    public void visit(PoliceStation policeStation, Field field) {
        field.unregister(policeman.getCoordinates());
        policeman.setState(new WaitingState(policeman));
    }
}
