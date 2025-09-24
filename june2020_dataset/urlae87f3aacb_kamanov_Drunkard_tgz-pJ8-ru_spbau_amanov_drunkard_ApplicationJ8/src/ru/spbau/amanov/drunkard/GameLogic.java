package ru.spbau.amanov.drunkard;

import ru.spbau.amanov.drunkard.GameObjects.*;

/**
 *  Class provides game logic.
 *
 *  @author  Karim Amanov
 */
public final class GameLogic extends CollisionAdaptor {

    /**
     *  Class constructor.
     * @param field game field.
     * @param pubCoord default pub coordinate.
     */
    public GameLogic(AbstractField field) {
        gameField = field;
        field.addObject(new Column(), GameConfig.COLUMN_POS);
        Lantern lantern = new Lantern(field, GameConfig.LANTERN_POS, GameConfig.LANTERN_RADIUS);
        field.addObject(lantern, GameConfig.LANTERN_POS);
        policeman = new Policeman(gameField, lantern);
        beggar = new Beggar(gameField);
        addNewDrunkard();
    }

    /**
     *  Make next game step.
     */
    public void nextStep() {
        updateGameObjects();
        if (stepCount == GameConfig.DRUNKARD_FREQ) {
            addNewDrunkard();
            stepCount = 0;
        }
        stepCount++;
    }

    @Override
    public void collisionWith(Drunkard drunkard) {
        if (!drunkard.isVisited()) {
            drunkard.randomMove();
        }
    }

    private void addNewDrunkard() {
        if (isAppearanceAvaible()) {
            Drunkard drunkard = new Drunkard(gameField, drunkardInitPos);
        }
    }

    private boolean isAppearanceAvaible() {
        return (gameField.getObject(drunkardInitPos) instanceof Empty);
    }

    private void updateGameObjects() {
        gameField.initFieldObjects();
        gameField.visitFieldObjects(this);
        policeman.update();
        beggar.update();
    }

    private AbstractField gameField;
    private Policeman policeman;
    private Beggar beggar;
    private int stepCount = 0;
    private final Position drunkardInitPos = GameConfig.PUB_POS;

}
