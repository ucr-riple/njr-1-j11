package DrunkardGame.States.PolicemanStates;

import DrunkardGame.GameObjects.CommonObjects.Coordinates;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameObject;
import DrunkardGame.GameObjects.MovableObjects.Policeman;
import DrunkardGame.States.DrunkardStates.DrunkardLyingState;
import DrunkardGame.Strategies.PolicemanStrategy.WaitingStrategy;
import com.sun.jndi.url.dns.dnsURLContext;

/**
 * Created by novokrest on 4/16/14.
 */
public class WaitingState extends PolicemanState {
    public WaitingState(Policeman policeman) {
        super(policeman);
        strategy = new WaitingStrategy();
    }

    public void makeStepHandle(Field field) {
        if (strategy.getOptimalStep(null, field) != null) { //lying or sleeping drunkard appeared
            field.getObject(policeman.getCoordinates()).accept(this, field);
        }
    }

    @Override
    public void visit(GameObject gameObject, Field field) {
        field.register(policeman);
        policeman.setState(new GoingToDrunkardState(policeman));
        policeman.makeStep(field);

    }
}
