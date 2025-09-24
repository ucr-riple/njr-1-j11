package DrunkardGame.GameObjects.CommonObjects;

import DrunkardGame.Factories.BeggarFactory;
import DrunkardGame.Factories.DrunkardFactory;
import DrunkardGame.Factories.PolicemanFactory;
import DrunkardGame.GameInterfaces.IGameFactory;
import DrunkardGame.GameInterfaces.IGameObservable;

import java.util.ArrayList;

/**
 * Created by novokrest on 3/3/14.
 */
public class Game implements IGameObservable {
    public int step = 0;
    Field field;
    ArrayList<GameMovingObject> movingObjects;
    IGameFactory drunkardFactory;
    IGameFactory policemanFactory;
    IGameFactory beggarFactory;

    public Game() {
        field = new Field(17, 17);
        movingObjects = new ArrayList<GameMovingObject>();
        drunkardFactory = new DrunkardFactory(10, 1);
        policemanFactory = new PolicemanFactory(15, 4);
        beggarFactory = new BeggarFactory(1, 5);

        GameMovingObject policeman = policemanFactory.getObject(field);
        GameMovingObject beggar = beggarFactory.getObject(field);
        registerGameObject(policeman);
        registerGameObject(beggar);
        //field.register(policeman);
        field.register(beggar);
    }

    public void start() {
        field.print();
        while (true) {
            //try { Thread.sleep(300); } catch (Exception e) {}
            nextStep();
            field.print();
            step++;
            if (step == 500) {
                break;
            }
        }
    } 
    private void nextStep() {
        notifyAboutStep();
        if (step % 20 == 0) {
            GameMovingObject newDrunkard = drunkardFactory.getObject(field);
            if (newDrunkard != null) {
                field.register(newDrunkard);
                registerGameObject(newDrunkard);
            }
        }
    }

    @Override
    public void registerGameObject(GameMovingObject movingObject) {
        movingObjects.add(movingObject);
    }

    public void notifyAboutStep() {
        for (GameMovingObject movingObject : movingObjects) {
            movingObject.makeStep(field);
        }
    }
}
