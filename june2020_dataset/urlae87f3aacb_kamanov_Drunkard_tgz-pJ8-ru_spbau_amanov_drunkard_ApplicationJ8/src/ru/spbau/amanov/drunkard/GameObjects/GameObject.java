package ru.spbau.amanov.drunkard.GameObjects;

import ru.spbau.amanov.drunkard.CollisionAdaptor;
import ru.spbau.amanov.drunkard.ICollisionVisitor;
import ru.spbau.amanov.drunkard.Position;

/**
 *  Abstract class of all game objects.
 *
 *  @author  Karim Amanov
 */
public abstract class GameObject extends CollisionAdaptor {

    /**
     *  Visitor patterm method.
     * @param visitor Object that collisionWith this class.
     */
    public abstract void accept(ICollisionVisitor visitor);

    /**
     * @return current object coordinate.
     */
    public Position getPos() {
        return position;
    }

    /**
     *  Set current object coordinate.
     * @param vcoord
     */
    public void setPosition(Position pos) {
        position = new Position(pos);
    }

    /**
     *  This methods used in BFS search in the game field.
     *
     */
    public void setPrev(Position pos) {
        prev = pos;
    }
    public Position getPrev() {
        return prev;
    }
    public void setVisited(boolean f) {
        isVisited = f;
    }
    public boolean isVisited() {
        return isVisited;
    }

    private boolean isVisited = false;
    private Position prev;
    protected Position position;
}
