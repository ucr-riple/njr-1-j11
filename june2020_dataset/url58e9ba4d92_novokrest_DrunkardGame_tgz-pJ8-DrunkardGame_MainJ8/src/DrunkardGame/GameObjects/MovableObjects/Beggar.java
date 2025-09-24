package DrunkardGame.GameObjects.MovableObjects;

import DrunkardGame.GameInterfaces.IGameState;
import DrunkardGame.GameInterfaces.IGameMoving;
import DrunkardGame.GameInterfaces.IGameVisitable;
import DrunkardGame.GameInterfaces.IGameVisitor;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameMovingObject;
import DrunkardGame.GameObjects.CommonObjects.GameObject;
import DrunkardGame.States.BeggarStates.BeggarFindingBottleState;

/**
 * Created by novokrest on 4/12/14.
 */
public class Beggar extends GameMovingObject implements IGameMoving, IGameVisitable {

    int money;
    IGameState beggarState;

    public Beggar(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        money = 0;
        beggarState = new BeggarFindingBottleState(this);
    }

    public int getMoney() { return money; }
    public void setMoney(int money) { this.money = money; }
    public void spendSomeMoney(int expense) { money -= expense; }
    public void setState(IGameState state) { beggarState = state; }

    @Override
    public void makeStep(Field field) {
        beggarState.makeStepHandle(field);
    }

    @Override
    public void accept(IGameVisitor visitor, Field field) {
        visitor.visit(this, field);
    }

    @Override
    public void print() {
        System.out.print('W');
    }
}
