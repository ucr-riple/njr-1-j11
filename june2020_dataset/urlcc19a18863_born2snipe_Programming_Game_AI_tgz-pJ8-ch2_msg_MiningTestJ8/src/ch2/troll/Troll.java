package ch2.troll;

public class Troll {
    private State state;

    public void update() {
        state.execute(this);
    }

    public void changeState(State state) {
        this.state = state;
    }

    public boolean isSafe() {
        return false;
    }

    public void moveAwayFromEnemy() {

    }

    public boolean isThreatend() {
        return !isSafe();
    }

    public void snore() {

    }
}
