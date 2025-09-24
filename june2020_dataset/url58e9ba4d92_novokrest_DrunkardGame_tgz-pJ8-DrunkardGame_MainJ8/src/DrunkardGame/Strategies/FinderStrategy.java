package DrunkardGame.Strategies;

import DrunkardGame.GameInterfaces.IGameState;
import DrunkardGame.GameInterfaces.IGameStrategy;
import DrunkardGame.GameInterfaces.IGameVisitor;
import DrunkardGame.GameObjects.CommonObjects.Coordinates;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.GameObject;
import DrunkardGame.States.DrunkardStates.DrunkardWalkingState;
import DrunkardGame.States.DrunkardStates.DrunkardLyingState;
import DrunkardGame.States.DrunkardStates.DrunkardSleepingState;
import DrunkardGame.GameObjects.MovableObjects.Beggar;
import DrunkardGame.GameObjects.MovableObjects.Drunkard;
import DrunkardGame.GameObjects.MovableObjects.Policeman;
import DrunkardGame.GameObjects.StaticObjects.*;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Admin on 4/13/14.
 */
public abstract class FinderStrategy implements IGameVisitor, IGameStrategy {
    protected Coordinates targetObjectCoordinates;
    protected boolean[][] isVisit;
    protected GameObject[][] predecessors;
    protected Queue<GameObject> objectsForVisit;

    public FinderStrategy() {
        targetObjectCoordinates = null;
        objectsForVisit = new LinkedList<GameObject>();
    }

    @Override
    public Coordinates getOptimalStep (Coordinates startCoordinates,  Field field) {
        initForCalculatePath(field);
        traverseField(startCoordinates, field);
        if (targetObjectCoordinates != null) {
            Coordinates nextCoordinates = getOptimalStepToTarget();
            return nextCoordinates;
        }
        return startCoordinates;
    }

    private void initForCalculatePath(Field field) {
        targetObjectCoordinates = null;
        objectsForVisit.clear();
        isVisit = new boolean[field.getRowCount()][field.getColumnCount()];
        predecessors = new GameObject[field.getRowCount()][field.getColumnCount()];
        for (int i = 0; i < field.getRowCount(); i++) {
            for (int j = 0; j < field.getColumnCount(); j++) {
                isVisit[i][j] = false;
                predecessors[i][j] = null;
            }
        }
    }

    private Coordinates getOptimalStepToTarget() {
        Coordinates currentObjectCoordinates = targetObjectCoordinates;
        while (getPredecessor(getPredecessor(currentObjectCoordinates).getCoordinates()) != null) {
            currentObjectCoordinates = getPredecessor(currentObjectCoordinates).getCoordinates();
        }
        return new Coordinates(currentObjectCoordinates.getX(), currentObjectCoordinates.getY());
    }

    private GameObject getPredecessor(Coordinates gameObjectCoordinates) {
        return predecessors[gameObjectCoordinates.getX()][gameObjectCoordinates.getY()];
    }

    private void traverseField (Coordinates startCoordinates, Field field) {
        int startX = startCoordinates.getX();
        int startY = startCoordinates.getY();
        isVisit[startX][startY] = true;
        addNeighborsFromFieldForVisit(field.getObject(startX, startY), field);
        while (!objectsForVisit.isEmpty() && (targetObjectCoordinates == null)) {
            GameObject objectForVisit = objectsForVisit.poll();
            objectForVisit.accept(this, field);
        }
    }

    private void addNeighborsFromFieldForVisit (GameObject predecessor, Field field) {
        int currentX = predecessor.getX();
        int currentY = predecessor.getY();
        addObjectFromFieldForVisit(currentX - 1, currentY, predecessor, field);
        addObjectFromFieldForVisit(currentX + 1, currentY, predecessor, field);
        addObjectFromFieldForVisit(currentX, currentY - 1, predecessor, field);
        addObjectFromFieldForVisit(currentX, currentY + 1, predecessor, field);
    }

    private void addObjectFromFieldForVisit (int coordinateX, int coordinateY, GameObject predecessor, Field field) {
        if (! isVisit[coordinateX][coordinateY]) {
            objectsForVisit.add(field.getObject(coordinateX, coordinateY));
            isVisit[coordinateX][coordinateY] = true;
            predecessors[coordinateX][coordinateY] = predecessor;
        }
    }

    @Override
    public void visit(GameObject gameObject, Field field) {
        addNeighborsFromFieldForVisit(gameObject, field);
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
    public void visit(DrunkardWalkingState state, Field field) {

    }

    @Override
    public void visit(DrunkardLyingState state, Field field) {

    }

    @Override
    public void visit(DrunkardSleepingState state, Field field) {

    }

    @Override
    public void visit(Beggar beggar, Field field) {
    }

    @Override
    public void visit(Policeman policeman, Field field) {

    }

}
