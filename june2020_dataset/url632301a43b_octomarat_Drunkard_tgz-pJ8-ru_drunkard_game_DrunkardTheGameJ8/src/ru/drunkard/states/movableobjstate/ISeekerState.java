package ru.drunkard.states.movableobjstate;

import ru.drunkard.field.GameField;
import ru.drunkard.fieldobjects.ISeekerWithState;

public interface ISeekerState {
    public void initActions(ISeekerWithState sws, GameField field);
}
