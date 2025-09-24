package DrunkardGame.GameObjects.MovableObjects;

import DrunkardGame.GameInterfaces.IGameMoving;
import DrunkardGame.GameInterfaces.IGameState;
import DrunkardGame.GameInterfaces.IGameVisitable;
import DrunkardGame.GameInterfaces.IGameVisitor;
import DrunkardGame.GameObjects.CommonObjects.Coordinates;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameMovingObject;
import DrunkardGame.GameObjects.CommonObjects.GameObject;
import DrunkardGame.GameObjects.StaticObjects.Bottle;
import DrunkardGame.States.DrunkardStates.DrunkardState;
import DrunkardGame.States.DrunkardStates.DrunkardWalkingState;

/**
 * Created by novokrest on 3/2/14.
 */
public class Drunkard extends GameMovingObject implements IGameMoving, IGameVisitable {
    boolean hasBottle;
    public DrunkardState drunkardState;

    public Drunkard(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        hasBottle = true;
        drunkardState = new DrunkardWalkingState(this);
    }

    public void setState(DrunkardState state) { drunkardState = state; }

    public void tryToLoseBottle(Coordinates coordinates, Field field) {
        if (hasBottle && (Math.random() < 0.0333)) {
            hasBottle = false;
            field.register(new Bottle(coordinates.getX(), coordinates.getY()));
        }
    }

    @Override
    public void makeStep(Field field) {
        drunkardState.makeStepHandle(field);
    }

    @Override
    public void accept(IGameVisitor visitor, Field field) {
        drunkardState.accept(visitor, field);
    }

    public void print() {
        drunkardState.print();
    }
}
