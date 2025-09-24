package DrunkardGame.States.BeggarStates;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameObject;
import DrunkardGame.GameObjects.MovableObjects.Beggar;

/**
 * Created by Admin on 4/13/14.
 */
public class BeggarSpendingMoneyState extends BeggarState {

    public BeggarSpendingMoneyState(Beggar beggar) {
        super(beggar);
    }

    public void makeStepHandle (Field field) {
        if (beggar.getMoney() == 0) {
            field.getObject(beggar.getCoordinates()).accept(this, field); //near GlassPoint
        }
        beggar.spendSomeMoney(1);
    }

    @Override
    public void visit(GameObject gameObject, Field field) {
        beggar.setState(new BeggarFindingBottleState(beggar));
        field.register(beggar);
        beggar.makeStep(field);
    }
}
