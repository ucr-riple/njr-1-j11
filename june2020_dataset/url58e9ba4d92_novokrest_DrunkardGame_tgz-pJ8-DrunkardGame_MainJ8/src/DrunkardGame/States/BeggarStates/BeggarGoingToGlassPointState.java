package DrunkardGame.States.BeggarStates;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.MovableObjects.Beggar;
import DrunkardGame.GameObjects.StaticObjects.*;
import DrunkardGame.Strategies.BeggarStrategies.GlassPointFinderStrategy;

/**
 * Created by Admin on 4/13/14.
 */
public class BeggarGoingToGlassPointState extends BeggarState {

    public BeggarGoingToGlassPointState(Beggar beggar) {
        super(beggar);
        beggarStrategy = new GlassPointFinderStrategy();
    }

    @Override
    public void visit(GlassPoint glassPoint, Field field) {
        field.unregister(beggar.getCoordinates());
        beggar.setState(new BeggarSpendingMoneyState(beggar));
        beggar.setMoney(30);
    }
}
