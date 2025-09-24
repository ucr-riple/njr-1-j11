package ru.spbau.amanov.drunkard.GameObjects;

import ru.spbau.amanov.drunkard.*;

import java.util.Random;

/**
 *  Class provides game object drunkard.
 *
 *  @author  Karim Amanov
 */
public class Drunkard extends GameObject {

    /**
     *  Drunkard state enumerator.
     */
    public enum State {ACTIVE, SLEEP, LYING}

    public Drunkard(AbstractField f, Position pos) {
        field = f;
        position = new Position(pos);
        field.addObject(this, position);
        pathHelper = new PathHelper(field);
    }

    @Override
    public void accept(ICollisionVisitor visitor) {
        visitor.collisionWith(this);
    }

    /**
     *  Make random move.
     */
    public void randomMove() {
        if (state != State.ACTIVE) {
            return;
        }

        nextPosition = pathHelper.generateRandomMove(position);
        GameObject collisionObj = field.getObject(nextPosition);
        collisionObj.accept(this);
        setVisited(true);
    }


    /**
     * Set current drunkard state.
     * @param s
     */
    public void setState(State s) {
        state = s;
    }

    /**
     * @return current drunckard state.
     */
    public State getState() {
        return state;
    }

    @Override
    public void collisionWith(Drunkard obj) {
        switch (obj.getState()) {
            case SLEEP : state = State.SLEEP;
                break;
            default : break;
        };
    }

    @Override
    public void collisionWith(Column obj) {
        state = State.SLEEP;
    }

    @Override
    public void collisionWith(Bottle obj) {
        state = State.LYING;
    }

    @Override
    public void collisionWith(Empty obj) {
        field.removeObject(position);
        field.addObject(this, nextPosition);
        tryToLooseBottle();
        position = nextPosition;
    }

    public boolean isLying() {
        return state == State.LYING;
    }

    public boolean isSleep() {
        return state == State.SLEEP;
    }

    private void tryToLooseBottle() {
        int rand = randomGenerator.nextInt(GameConfig.BOTTLE_LOOSE_PROBABILITY);
        if (rand == 1) {
            if (bottle != null) {
                bottle.setPosition(position);
                field.addObject(bottle, position);
                bottle = null;
            }
        }
    }


    private Random randomGenerator = new Random();
    private Position nextPosition;
    private Bottle bottle = new Bottle();
    private State state = State.ACTIVE;
    private AbstractField field;
    private PathHelper pathHelper;

}
