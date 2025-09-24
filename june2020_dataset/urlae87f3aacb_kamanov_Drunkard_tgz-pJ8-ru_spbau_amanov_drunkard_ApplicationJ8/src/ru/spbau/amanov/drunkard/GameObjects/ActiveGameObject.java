package ru.spbau.amanov.drunkard.GameObjects;

import ru.spbau.amanov.drunkard.AbstractField;
import ru.spbau.amanov.drunkard.RectangleField;
import ru.spbau.amanov.drunkard.PathHelper;
import ru.spbau.amanov.drunkard.Position;

/**
 *  Abstract Class for active game objects.
 *
 *  @author  Karim Amanov
 */
public abstract class ActiveGameObject extends GameObject {

    public ActiveGameObject(AbstractField f, Position initPos) {
        field = f;
        pathHelper = new PathHelper(field);
        INIT_POS = initPos;
        state = State.HIDE;
    }

    /**
     * Update object state.
     */
    abstract public void update();

    /**
     * Find target.
     */
    abstract protected void lookForTarget();

    /**
     * Hide object from game field.
     */
    abstract protected void hide();

    /**
     * Try to add object to the game field.
     */
    abstract protected void tryToAppear();

    /**
     * Invoke when the path to the target not found.
     */
    abstract protected void pathNotFound();

    protected void move() {
        Position nextPos = pathHelper.getNextPos(position, targetPos);
        if (nextPos == null) {
            pathNotFound();
            return;
        }
        field.removeObject(position);
        field.addObject(this, nextPos);
        position = nextPos;
        if (position.equals(targetPos)) {
            targetReached();
        }
    }

    protected void hide(State resultState) {
        field.removeObject(position);
        state = resultState;
        position = null;
        targetPos = null;
    }

    protected void targetReached() {
        if (state == State.GO_TO_TARGET) {
            state = State.GO_TO_INIT_POS;
            targetPos = INIT_POS;
        } else if (state == State.GO_TO_INIT_POS) {
            state = State.INIT_POS;
        }
    }

    protected void tryToAppear(State resultState) {
        if (field.getObject(INIT_POS) instanceof Empty) {
            position = INIT_POS;
            field.addObject(this, position);
            state = resultState; //State.LOOK_FOR_TARGET;
        }
    }

    protected void setTarget(Position pos) {
        targetPos = pos;
    }

    enum State {
        GO_TO_TARGET,
        GO_TO_INIT_POS,
        LOOK_FOR_TARGET,
        INIT_POS,
        HIDE
    }

    protected AbstractField field;
    protected State state;
    private Position targetPos;
    private final Position INIT_POS;
    private PathHelper pathHelper;
}
