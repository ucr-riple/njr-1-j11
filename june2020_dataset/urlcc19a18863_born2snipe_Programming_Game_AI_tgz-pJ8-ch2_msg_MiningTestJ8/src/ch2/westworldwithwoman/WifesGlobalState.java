package ch2.westworldwithwoman;

public class WifesGlobalState implements State<MinersWife> {
    private static final WifesGlobalState instance = new WifesGlobalState();

    public static WifesGlobalState instance() {
        return instance;
    }

    @Override
    public void enter(MinersWife obj) {

    }

    @Override
    public void execute(MinersWife obj) {
        if (Math.random() < 0.1) {
            obj.machine.changeState(VisitBathroom.instance());
        }
    }

    @Override
    public void exit(MinersWife obj) {

    }

}
