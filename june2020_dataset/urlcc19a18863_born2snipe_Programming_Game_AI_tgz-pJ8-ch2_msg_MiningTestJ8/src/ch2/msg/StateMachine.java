package ch2.msg;

class StateMachine<T extends BaseGameEntity> {

    private final T owner;
    private State<T> current;
    private State<T> previous;
    private State<T> global;

    public StateMachine(T owner) {
        this.owner = owner;
    }

    void handleMessage(Telegram telegram) {
        //first see if the current state is valid and that it can handle
        //the message
        if (current != null) {
            current.onMessage(owner, telegram);
        }

        //if not, and if a global state has been implemented, send
        //the message to the global state
        if (global != null) {
            global.onMessage(owner, telegram);
        }

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
