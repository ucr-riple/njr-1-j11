package ru.drunkard.states.movableobjstate;

import ru.drunkard.field.GameField;
import ru.drunkard.fieldobjects.ISeekerWithState;
import ru.drunkard.utility.Point;

public class TargetedState implements ISeekerState {
    private Point target;

    public TargetedState(Point target) { this.target = target; }
    public void initActions(ISeekerWithState sws, GameField field) {
        sws.goToTarget(target, field);
    }
}
