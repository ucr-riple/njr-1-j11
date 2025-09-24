package DrunkardGame.Strategies.PolicemanStrategy;

import DrunkardGame.GameInterfaces.IGameState;
import DrunkardGame.GameInterfaces.IGameStrategy;
import DrunkardGame.GameInterfaces.IGameVisitor;
import DrunkardGame.GameObjects.CommonObjects.Coordinates;
import DrunkardGame.GameObjects.CommonObjects.Field;
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
public class WaitingStrategy implements IGameStrategy, IGameVisitor{

    Coordinates drunkardCoordinate;

    @Override
    public Coordinates getOptimalStep(Coordinates oldCoordinates, Field field) {
        drunkardCoordinate = null;
        Lamppost lamppost = field.getLamppost();
        searchDrunkard(field, lamppost.getCoordinates(), lamppost.getLightingRadius());
        return drunkardCoordinate;
    }

    private void searchDrunkard(Field field, Coordinates centerCoordinates, int radius) {
        for (int i = centerCoordinates.getX() - radius; i <= centerCoordinates.getX() + radius; i++) {
            for (int j = centerCoordinates.getY() - radius; j <= centerCoordinates.getY() + radius; j++) {
                field.getObject(i, j).accept(this, field);
            }
        }
    }


    @Override
    public void visit(GameObject gameObject, Field field) {

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
    public void visit(DrunkardWalkingState drunkard, Field field) {

    }

    @Override
    public void visit(DrunkardLyingState drunkard, Field field) {
        drunkardCoordinate = drunkard.getCoordinates();
    }

    @Override
    public void visit(DrunkardSleepingState drunkard, Field field) {
        drunkardCoordinate = drunkard.getCoordinates();
    }

    @Override
    public void visit(Beggar beggar, Field field) {

    }

    @Override
    public void visit(Policeman policeman, Field field) {

    }
}
