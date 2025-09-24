package DrunkardGame.States.DrunkardStates;

import DrunkardGame.GameInterfaces.IGameVisitor;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.MovableObjects.Drunkard;

/**
 * Created by novokrest on 3/3/14.
 */
public class DrunkardSleepingState extends DrunkardState {

    public DrunkardSleepingState(Drunkard drunkard) {
        super(drunkard);
    }

    @Override
    public void accept(IGameVisitor visitor, Field field) {
        visitor.visit(this, field);
    }

    @Override
    public void print() {
        System.out.print('Z');
    }

}
