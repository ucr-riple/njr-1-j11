package ru.drunkard.fieldobjects;

import ru.drunkard.field.GameField;
import ru.drunkard.utility.Point;

public interface ISeekerWithState {
    public void waitSome(GameField field);
    public void exitStartPos(GameField field);
    public void seekTarget(GameField field);
    public void goToTarget(Point target, GameField field);
    public void returnToStartPos(GameField field);
    public void enterStartPos(GameField field);
}
