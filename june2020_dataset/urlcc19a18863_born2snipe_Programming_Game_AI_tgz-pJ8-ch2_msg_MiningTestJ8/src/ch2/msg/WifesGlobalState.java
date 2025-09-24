package ch2.msg;

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

    @Override
    public void onMessage(MinersWife obj, Telegram telegram) {
        switch(telegram.type) {
            case HI_HONEY_IM_HOME:
                System.out.println(obj.getId()+": Hi honey. Let me make you some of mah fine country stew");
                obj.machine.changeState(CookStew.instance());
                break;
        }
    }

}
