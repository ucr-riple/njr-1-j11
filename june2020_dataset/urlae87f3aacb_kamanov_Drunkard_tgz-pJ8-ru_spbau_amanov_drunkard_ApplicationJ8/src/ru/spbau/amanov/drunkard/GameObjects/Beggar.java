package ru.spbau.amanov.drunkard.GameObjects;

import ru.spbau.amanov.drunkard.AbstractField;
import ru.spbau.amanov.drunkard.GameConfig;
import ru.spbau.amanov.drunkard.RectangleField;
import ru.spbau.amanov.drunkard.ICollisionVisitor;

/**
 *  Class provides game object Beggar.
 *
 *  @author  Karim Amanov
 */
public class Beggar extends ActiveGameObject {

    public Beggar(AbstractField field) {
        super(field, GameConfig.BOTTLE_STORE_POS);
        tryToAppear();
    }

    @Override
    public void update() {
        if (state == State.LOOK_FOR_TARGET) {
            lookForTarget();
        }

        if (state == State.HIDE) {
            if (hideCount < GameConfig.BEGGAR_PERIOD) {
                hideCount++;
            } else {
                tryToAppear();
                hideCount = 0;
            }
        } else if (state == State.INIT_POS) {
            hide();
        } else if (state == State.GO_TO_INIT_POS || state == State.GO_TO_TARGET ) {
            move();
        }
    }

    @Override
    protected void pathNotFound() {
        if (state == State.GO_TO_TARGET) {
            state = State.LOOK_FOR_TARGET;
        }
    }


    @Override
    protected void hide() {
        super.hide(State.HIDE);
    }

    @Override
    protected void lookForTarget() {
        field.visitFieldObjects(this);
    }

    @Override
    protected void tryToAppear() {
        super.tryToAppear(State.LOOK_FOR_TARGET);
    }

    @Override
    public void accept(ICollisionVisitor visitor) {
        visitor.collisionWith(this);
    }

    @Override
    public void collisionWith(Bottle bottle) {
        setTarget(bottle.getPos());
        state = State.GO_TO_TARGET;
    }

    private int hideCount;
}
