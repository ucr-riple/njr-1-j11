package test;

/**
 * Represents an order's current state.
 */
public enum OrderState {

    /** Being prepared by the user and has not been submitted yet. */
    DRAFT,
    
    /** Pending execution in a fixing. */
    PENDING,

    /** Successfully executed order. */
    DONE(true),
    
    /** Client initiated cancellation of an order before execution. */
    CANCELLED(true),
    
    /** Failed to process the order. */
    REJECTED(true);

    private final boolean isTerminal;

    OrderState(boolean terminal) {
        isTerminal = terminal;
    }

    OrderState() {
        isTerminal = false;
    }

    /**
     * Determines whether the order is in a terminal state.
     * @return true if the state is terminal
     */
    public boolean isTerminal() {
        return isTerminal;
    }
}