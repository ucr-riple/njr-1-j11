package ru.spbau.amanov.drunkard.GameObjects;

import ru.spbau.amanov.drunkard.ICollisionVisitor;

/**
 *  Class provides game object bottle.
 *
 *  @author  Karim Amanov
 */
public class Bottle extends GameObject {

    @Override
    public void accept(ICollisionVisitor visitor) {
        visitor.collisionWith(this);
    }
}
