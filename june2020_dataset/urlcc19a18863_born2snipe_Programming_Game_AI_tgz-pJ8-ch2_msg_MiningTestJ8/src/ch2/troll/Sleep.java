package ch2.troll;

public class Sleep implements State {

    @Override
    public void execute(Troll obj) {
        if (obj.isThreatend()) {
            obj.changeState(new RunAway());
        } else {
            obj.snore();
        }
    }

}
