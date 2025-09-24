package DrunkardGame.States.BeggarStates;

import DrunkardGame.GameInterfaces.IGameState;
import DrunkardGame.GameInterfaces.IGameStrategy;
import DrunkardGame.GameObjects.CommonObjects.Coordinates;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameObject;
import DrunkardGame.GameObjects.MovableObjects.Beggar;
import DrunkardGame.GameObjects.MovableObjects.Drunkard;
import DrunkardGame.GameObjects.MovableObjects.Policeman;
import DrunkardGame.GameObjects.StaticObjects.*;
import DrunkardGame.States.DrunkardStates.DrunkardWalkingState;
import DrunkardGame.States.DrunkardStates.DrunkardLyingState;
import DrunkardGame.States.DrunkardStates.DrunkardSleepingState;

/**
 * Created by novokrest on 4/15/14.
 */
public class BeggarState implements IGameState {
    Beggar beggar;
    IGameStrategy beggarStrategy;

    public BeggarState(Beggar beggar) {
        this.beggar = beggar;
    }

    public void makeStepHandle(Field field) {
        Coordinates nextCoordinates = beggarStrategy.getOptimalStep(beggar.getCoordinates(), field);
        field.getObject(nextCoordinates).accept(this, field);
    }

    @Override
    public void visit(GameObject gameObject, Field field) {
        field.swapGameObject(gameObject.getCoordinates(), beggar.getCoordinates());
    }

    @Override
    public void visit(Border border, Field field) {

    }

    @Override
    public void visit(Bottle bottle, Field field) {

    }

    @Override
    public void visit(Column column, Field field) {

    }

    @Override
    public void visit(GlassPoint glassPoint, Field field) {

    }

    @Override
    public void visit(Lamppost lamppost, Field field) {

    }

    @Override
    public void visit(PoliceStation policeStation, Field field) {

    }

    @Override
    public void visit(Pub pub, Field field) {

    }

    @Override
    public void visit(Drunkard drunkard, Field field) {

    }

    @Override
    public void visit(IGameState state, Field field) {

    }

    @Override
    public void visit(DrunkardWalkingState state, Field field) {

    }

    @Override
    public void visit(DrunkardLyingState state, Field field) {

    }

    @Override
    public void visit(DrunkardSleepingState state, Field field) {

    }

    @Override
    public void visit(Beggar beggar, Field field) {

    }

    @Override
    public void visit(Policeman policeman, Field field) {

    }
}
