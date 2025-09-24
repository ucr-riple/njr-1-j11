package DrunkardGame.GameObjects.MovableObjects;

import DrunkardGame.GameInterfaces.*;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameMovingObject;
import DrunkardGame.GameObjects.CommonObjects.GameObject;
import DrunkardGame.States.PolicemanStates.WaitingState;

/**
 * Created by novokrest on 4/12/14.
 */
public class Policeman extends GameMovingObject implements IGameMoving {
    boolean withDrunkard;
    IGameState state;

    public Policeman(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        state = new WaitingState(this);
    }

    public void setState (IGameState state) { this.state = state; }

    @Override
    public void makeStep(Field field) {
        state.makeStepHandle(field);
    }

    @Override
    public void accept(IGameVisitor visitor, Field field) {
        visitor.visit(this, field);
    }

    @Override
    public void print() {
        System.out.print('P');
    }


}
