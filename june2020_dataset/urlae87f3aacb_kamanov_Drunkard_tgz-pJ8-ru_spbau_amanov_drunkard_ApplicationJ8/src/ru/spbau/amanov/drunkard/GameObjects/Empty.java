package ru.spbau.amanov.drunkard.GameObjects;

import ru.spbau.amanov.drunkard.ICollisionVisitor;

/**
 *  Class provides empty game object.
 *
 *  @author  Karim Amanov
 */
public class Empty extends GameObject {
    @Override
    public void accept(ICollisionVisitor visitor) {
        visitor.collisionWith(this);
    }
}
