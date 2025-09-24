package ch2.msg;

class GoHomeAndSleepTilRested implements State<Miner> {

    private static final GoHomeAndSleepTilRested instance = new GoHomeAndSleepTilRested();

    public static GoHomeAndSleepTilRested instance() {
        return instance;
    }

    @Override
    public void enter(Miner obj) {
        if (obj.getLocation() != Location.SHACK) {
            System.out.println(obj.getId() + ": Walkin' home");
            obj.changeLocation(Location.SHACK);
            MessageDispatcher.instance().dispatch(obj.getId(), "Elsa", MessageType.HI_HONEY_IM_HOME, 0.0);
        }
    }

    @Override
    public void exit(Miner obj) {
        System.out.println(obj.getId() + ": Leaving the house");
    }

    @Override
    public void execute(Miner obj) {
        //if miner is not fatigued start to dig for nuggets again.
        if (!obj.isFatigued()) {
            System.out.println(obj.getId() + ": What a God darn fantastic nap! Time to find more gold");
            obj.changeState(EnterMineAndDigForNugget.instance());
        } else {
            obj.sleep();
            System.out.println(obj.getId() + ": ZZZZ...");
        }

    }

    @Override
    public void onMessage(Miner obj, Telegram telegram) {
        switch (telegram.type) {
            case STEW_READY:
                System.out.println(obj.getId() + ": Okay hun, ahm a-comin'!");
                obj.machine.changeState(EatStew.instance());
                break;
        }
    }
}
