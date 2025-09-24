package ru.spbau.amanov.drunkard;

import ru.spbau.amanov.drunkard.GameObjects.*;

/**
 *  Interface for all visitors.
 *
 *  @author  Karim Amanov
 */
public interface ICollisionVisitor {

    /**
     * Process accepted object.
     * @param obj accepted object.
     */
    public void collisionWith(Drunkard obj);
    public void collisionWith(Column obj);
    public void collisionWith(Bottle obj);
    public void collisionWith(Empty obj);
    public void collisionWith(Lantern obj);
    public void collisionWith(Policeman obj);
    public void collisionWith(Beggar obj);


}
