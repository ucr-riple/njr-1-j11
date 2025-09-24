package ru.spbau.amanov.drunkard;

import ru.spbau.amanov.drunkard.GameObjects.*;

/**
 *  Class provides adaptor for ICollisionVisitor interface.
 *
 *  @author  Karim Amanov
 */
public class CollisionAdaptor implements ICollisionVisitor {
    @Override
    public void collisionWith(Drunkard obj) {
    }

    @Override
    public void collisionWith(Column obj) {
    }

    @Override
    public void collisionWith(Bottle obj) {
    }

    @Override
    public void collisionWith(Empty obj) {
    }

    @Override
    public void collisionWith(Lantern obj) {
    }

    @Override
    public void collisionWith(Policeman obj) {
    }

    @Override
    public void collisionWith(Beggar obj) {
    }
}
