package DrunkardGame.States.DrunkardStates;

import DrunkardGame.GameInterfaces.*;
import DrunkardGame.GameObjects.CommonObjects.Coordinates;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameObject;
import DrunkardGame.GameObjects.MovableObjects.Drunkard;
import DrunkardGame.GameObjects.StaticObjects.Bottle;
import DrunkardGame.GameObjects.StaticObjects.Column;
import DrunkardGame.Strategies.DrunkardStrategies.DrunkardWalkingStrategy;

/**
 * Created by novokrest on 3/3/14.
 */
public class DrunkardWalkingState extends DrunkardState {

    public DrunkardWalkingState(Drunkard drunkard) {
        super(drunkard);
        drunkardStrategy = new DrunkardWalkingStrategy();
    }

    @Override
    public void makeStepHandle(Field field) {
        Coordinates nextCoordinates = drunkardStrategy.getOptimalStep(drunkard.getCoordinates(), field);
        field.getObject(nextCoordinates).accept(this, field);
    }

    @Override
    public void accept(IGameVisitor visitor, Field field) {
        visitor.visit(this, field);
    }

    @Override
    public void visit(GameObject gameObject, Field field) {
        field.swapGameObject(gameObject.getCoordinates(), drunkard.getCoordinates());
        drunkard.tryToLoseBottle(gameObject.getCoordinates(), field);
    }

    @Override
    public void visit(Bottle bottle, Field field) {
        drunkard.setState(new DrunkardLyingState(drunkard));
    }

    @Override
    public void visit(Column column, Field field) {
        drunkard.setState(new DrunkardSleepingState(drunkard));
    }

    @Override
    public void visit(DrunkardLyingState state, Field field) {

    }

    @Override
    public void visit(DrunkardSleepingState state, Field field) {
        drunkard.setState(new DrunkardSleepingState(drunkard));
    }

    @Override
    public void print() {
        System.out.print('D');
    }
}
