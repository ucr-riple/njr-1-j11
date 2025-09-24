package DrunkardGame.GameInterfaces;

import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameObject;
import DrunkardGame.States.DrunkardStates.DrunkardWalkingState;
import DrunkardGame.States.DrunkardStates.DrunkardLyingState;
import DrunkardGame.States.DrunkardStates.DrunkardSleepingState;
import DrunkardGame.GameObjects.MovableObjects.Beggar;
import DrunkardGame.GameObjects.MovableObjects.Drunkard;
import DrunkardGame.GameObjects.MovableObjects.Policeman;
import DrunkardGame.GameObjects.StaticObjects.*;

/**
 * Created by novokrest on 3/3/14.
 */
public interface IGameVisitor {
    public void visit(GameObject gameObject, Field field);

    public void visit(Border border, Field field);
    public void visit(Bottle bottle, Field field);
    public void visit(Column column, Field field);
    public void visit(GlassPoint glassPoint, Field field);
    public void visit(Lamppost lamppost, Field field);
    public void visit(PoliceStation policeStation, Field field);
    public void visit(Pub pub, Field field);

    public void visit(Drunkard drunkard, Field field);
    public void visit(IGameState state, Field field);
    public void visit(DrunkardWalkingState state, Field field);
    public void visit(DrunkardLyingState state, Field field);
    public void visit(DrunkardSleepingState state, Field field);

    public void visit(Beggar beggar, Field field);
    public void visit(Policeman policeman, Field field);

}
