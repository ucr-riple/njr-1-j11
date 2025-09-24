package com.teamdev.students.calculator.impl;

import com.teamdev.students.fsm.TransitionMatrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.teamdev.students.calculator.impl.State.*;
import static java.util.EnumSet.noneOf;
import static java.util.EnumSet.of;


public class EvaluationMatrix implements TransitionMatrix<State> {

    private final Map<State, Set<State>> transitions = new HashMap<State, Set<State>>() {{
        put(START, of(LEFT_PARENTHESIS, NUMBER, FUNCTION));
        put(NUMBER, of(BINARY_OPERATOR, RIGHT_PARENTHESIS,
                FUNCTION_RIGHT_PARENTHESIS, ARGUMENT_SEPARATOR, FINISH));

        put(BINARY_OPERATOR, of(NUMBER, LEFT_PARENTHESIS, FUNCTION));
        put(LEFT_PARENTHESIS, of(NUMBER, FUNCTION, LEFT_PARENTHESIS));
        put(RIGHT_PARENTHESIS, of(BINARY_OPERATOR, RIGHT_PARENTHESIS,
                FUNCTION_RIGHT_PARENTHESIS, ARGUMENT_SEPARATOR, FINISH));

        put(FUNCTION, of(FUNCTION_LEFT_PARENTHESIS));
        put(FUNCTION_LEFT_PARENTHESIS, of(NUMBER, LEFT_PARENTHESIS, FUNCTION));
        put(FUNCTION_RIGHT_PARENTHESIS, of(ARGUMENT_SEPARATOR, BINARY_OPERATOR,
                FUNCTION_RIGHT_PARENTHESIS, RIGHT_PARENTHESIS, FINISH));

        put(ARGUMENT_SEPARATOR, of(NUMBER, LEFT_PARENTHESIS, FUNCTION));
        put(FINISH, noneOf(State.class));
    }};

    @Override
    public State getStartState() {
        return START;
    }

    @Override
    public State getFinishState() {
        return FINISH;
    }

    @Override
    public Set<State> getPossibleStates(State state) {
        return transitions.get(state);
    }
}

