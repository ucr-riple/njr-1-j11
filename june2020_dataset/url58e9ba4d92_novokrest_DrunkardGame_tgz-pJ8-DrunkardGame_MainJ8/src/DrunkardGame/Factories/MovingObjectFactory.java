package DrunkardGame.Factories;

import DrunkardGame.GameInterfaces.IGameFactory;
import DrunkardGame.GameInterfaces.IGameState;
import DrunkardGame.GameInterfaces.IGameVisitor;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameMovingObject;
import DrunkardGame.GameObjects.CommonObjects.GameObject;
import DrunkardGame.GameObjects.MovableObjects.Beggar;
import DrunkardGame.GameObjects.MovableObjects.Drunkard;
import DrunkardGame.GameObjects.MovableObjects.Policeman;
import DrunkardGame.GameObjects.StaticObjects.*;
import DrunkardGame.States.DrunkardStates.DrunkardLyingState;
import DrunkardGame.States.DrunkardStates.DrunkardSleepingState;
import DrunkardGame.States.DrunkardStates.DrunkardWalkingState;

/**
 * Created by novokrest on 4/16/14.
 */
public abstract class MovingObjectFactory implements IGameFactory, IGameVisitor{
    int movingObjectX;
    int movingObjectY;
    boolean canCreate;

    public MovingObjectFactory(int movingObjectX, int movingObjectY) {
        this.movingObjectX = movingObjectX;
        this.movingObjectY = movingObjectY;
    }

    abstract GameMovingObject makeObject();

    @Override
    public GameMovingObject getObject(Field field) {
        canCreate = false;
        field.getObject(movingObjectX, movingObjectY).accept(this, field);
        return canCreate ? makeObject() : null;
    }

    @Override
    public void visit(GameObject gameObject, Field field) {
        canCreate = true;
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
