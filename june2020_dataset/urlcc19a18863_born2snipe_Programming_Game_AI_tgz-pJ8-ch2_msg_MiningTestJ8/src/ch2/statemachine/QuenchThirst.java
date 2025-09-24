package ch2.statemachine;

class QuenchThirst implements State<Miner> {

    private static final QuenchThirst instance = new QuenchThirst();

    public static QuenchThirst instance() {
        return instance;
    }

    @Override
    public void enter(Miner obj) {
        if (obj.getLocation() != Location.SALOON) {
            obj.changeLocation(Location.SALOON);
            System.out.println(obj.getId() + ": Boy, ah sure is thursty! Walking to the saloon");
        }
    }

    @Override
    public void exit(Miner obj) {
        System.out.println(obj.getId() + ": Leaving the saloon, feelin' good");
    }

    @Override
    public void execute(Miner obj) {
        if (obj.isThirsty()) {
            obj.buyAndDrinkAWhiskey();
            System.out.println(obj.getId() + ": That's mighty fine sippin liquer");
            obj.changeState(EnterMineAndDigForNugget.instance());
        }
    }
}
