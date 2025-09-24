package com.teamdev.students.fsm;

public interface StateAcceptor<State extends Enum,
        Context extends StateMachineContext<State, Context, TransitionError>,
        TransitionError extends Exception> {

    boolean acceptState(Context context, State possibleState) throws TransitionError;
}
