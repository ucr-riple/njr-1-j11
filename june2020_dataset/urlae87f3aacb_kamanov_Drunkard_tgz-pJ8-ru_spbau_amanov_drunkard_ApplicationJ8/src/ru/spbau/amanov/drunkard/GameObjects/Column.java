package ru.spbau.amanov.drunkard.GameObjects;

import ru.spbau.amanov.drunkard.ICollisionVisitor;

/**
 *  Class provides game object column.
 *
 *  @author  Karim Amanov
 */
public class Column extends GameObject {

    @Override
    public void accept(ICollisionVisitor visitor) {
        visitor.collisionWith(this);
    }
}
