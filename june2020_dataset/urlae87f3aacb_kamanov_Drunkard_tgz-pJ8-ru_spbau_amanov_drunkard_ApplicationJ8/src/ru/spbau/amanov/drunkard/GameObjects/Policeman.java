package ru.spbau.amanov.drunkard.GameObjects;

import ru.spbau.amanov.drunkard.AbstractField;
import ru.spbau.amanov.drunkard.GameConfig;
import ru.spbau.amanov.drunkard.RectangleField;
import ru.spbau.amanov.drunkard.ICollisionVisitor;

/**
 * Class provides game object policeman.
 *
 * @author  Karim Amanov
 */
public class Policeman extends ActiveGameObject {

    /**
     * Class constructor.
     * @param field game field.
     * @param l lanter that use this policeman.
     */
    public Policeman(AbstractField field, Lantern l) {
        super(field, GameConfig.POLICE_STATION_POS);
        lantern = l;
        state = State.LOOK_FOR_TARGET;
    }

    @Override
    public void update() {
        if (state == State.LOOK_FOR_TARGET) {
            lookForTarget();
        }

        if (state == State.INIT_POS) {
            hide();
        } else if (state == State.HIDE) {
            tryToAppear();
        } else if (state == State.GO_TO_INIT_POS || state == State.GO_TO_TARGET) {
            move();
        }
    }

    @Override
    protected void pathNotFound() {
        if (state == State.GO_TO_TARGET) {
            state = State.GO_TO_INIT_POS;
            setTarget(GameConfig.POLICE_STATION_POS);
        }
    }

    @Override
    protected void tryToAppear() {
        super.tryToAppear(State.GO_TO_TARGET);
    }

    @Override
    public void accept(ICollisionVisitor visitor) {
        visitor.collisionWith(this);
    }

    @Override
    protected void lookForTarget() {
        for (GameObject obj : lantern.getAlightObjects()) {
            obj.accept(this);
        }
    }

    @Override
    protected void hide() {
        super.hide(State.LOOK_FOR_TARGET);
    }

    @Override
    public void collisionWith(Drunkard drunkard) {
        if (drunkard.isLying() || drunkard.isSleep()) {
           setTarget(drunkard.getPos());
           state = State.HIDE;
        }
    }

    private Lantern lantern;
}
