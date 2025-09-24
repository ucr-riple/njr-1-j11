package ch2.westworldwithwoman;

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
}
