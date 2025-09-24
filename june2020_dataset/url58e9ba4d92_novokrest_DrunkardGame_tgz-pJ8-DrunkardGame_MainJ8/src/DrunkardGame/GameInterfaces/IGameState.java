package DrunkardGame.GameInterfaces;

import DrunkardGame.GameInterfaces.IGamePrintable;
import DrunkardGame.GameInterfaces.IGameStrategy;
import DrunkardGame.GameInterfaces.IGameVisitable;
import DrunkardGame.GameInterfaces.IGameVisitor;
import DrunkardGame.GameObjects.CommonObjects.Coordinates;
import DrunkardGame.GameObjects.CommonObjects.Field;
import DrunkardGame.GameObjects.CommonObjects.Game;

/**
 * Created by novokrest on 3/4/14.
 */

public interface IGameState extends IGameVisitor {
    void makeStepHandle(Field field);
}