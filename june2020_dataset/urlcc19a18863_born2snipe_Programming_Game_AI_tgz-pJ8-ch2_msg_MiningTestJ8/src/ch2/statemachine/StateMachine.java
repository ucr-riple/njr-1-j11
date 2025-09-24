package ch2.statemachine;

class StateMachine<T> {
    private final T owner;
    private State<T> current;
    private State<T> previous;
    private State<T> global;

    public StateMachine(T owner) {
        this.owner = owner;
    }

    void update() {
        if (global != null) {
            global.execute(owner);
        }
        if (current != null) {
            current.execute(owner);
        }
    }

    void changeState(State<T> state) {
        previous = current;
        current.exit(owner);
        current = state;
        current.enter(owner);
    }

    void revertToPreviousState() {
        changeState(previous);
    }

    boolean isInState(State<T> state) {
        return current.equals(state);
    }

    void setCurrentState(State<T> state) {
        this.current = state;
    }

    void setPreviousState(State<T> state) {
        this.previous = state;
    }

    void setGlobalState(State<T> state) {
        this.global = state;
    }
}
