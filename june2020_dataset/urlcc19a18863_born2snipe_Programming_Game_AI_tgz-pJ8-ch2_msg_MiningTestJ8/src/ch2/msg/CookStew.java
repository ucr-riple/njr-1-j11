package ch2.msg;

public class CookStew implements State<MinersWife> {

    private static final CookStew instance = new CookStew();

    public static CookStew instance() {
        return instance;
    }

    @Override
    public void enter(MinersWife obj) {
        //if not already cooking put the stew in the oven
        if (!obj.isCooking()) {
            System.out.println(obj.getId() + ": Puttin' the stew in the oven");
            //send a delayed message to myself so that I know when to take the stew
            //out of the oven
            MessageDispatcher.instance().dispatch(obj.getId(), "", MessageType.STEW_READY, 1.5);
            obj.setCooking(true);
        }
    }

    @Override
    public void execute(MinersWife obj) {
    }

    @Override
    public void exit(MinersWife obj) {
    }

    @Override
    public void onMessage(MinersWife obj, Telegram telegram) {
        switch (telegram.type) {
            case STEW_READY:
                System.out.println(obj.getId() + ": Stew ready! Let's eat");
                MessageDispatcher.instance().dispatch(obj.getId(), "", MessageType.STEW_READY, 0.0);
                obj.setCooking(false);
                obj.machine.changeState(DoHouseWork.instance());
                break;
        }
    }
}
