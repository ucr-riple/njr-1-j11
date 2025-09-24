package ch2.troll;

public class RunAway implements State {

    @Override
    public void execute(Troll obj) {
        if (obj.isSafe()) {
            obj.changeState(new Sleep());
        } else {
            obj.moveAwayFromEnemy();
        }
    }

}
