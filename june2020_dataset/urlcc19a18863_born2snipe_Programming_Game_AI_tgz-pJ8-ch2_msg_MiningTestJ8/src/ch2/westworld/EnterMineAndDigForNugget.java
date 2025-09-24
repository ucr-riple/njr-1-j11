package ch2.westworld;

class EnterMineAndDigForNugget implements State<Miner> {
    private static final EnterMineAndDigForNugget instance = new EnterMineAndDigForNugget();

    public static EnterMineAndDigForNugget instance() {
        return instance;
    }

    @Override
    public void enter(Miner obj) {
        //if the miner is not already located at the gold mine, he must
        //change location to the gold mine
        if (obj.getLocation() != Location.GOLDMINE) {
            System.out.println(obj.getId() + ": Walkin' to the gold mine");
            obj.changeLocation(Location.GOLDMINE);
        }
    }

    @Override
    public void exit(Miner obj) {
        System.out.println(obj.getId() + ": Ah'm leavin' the gold mine with mah pockets full o' sweet gold");
    }

    @Override
    public void execute(Miner obj) {
        //the miner digs for gold until he is carrying in excess of MaxNuggets.
        //If he gets thirsty during his digging he stops work and
        //changes state to go to the saloon for a whiskey.
        obj.addToGoldCarried(1);

        //diggin' is hard work
        obj.increaseFatigue();

        System.out.println(obj.getId() + ": Pickin' up a nugget");

        //if enough gold mined, go and put it in the bank
        if (obj.arePocketsFull()) {
            obj.changeState(VisitBankAndDepositGold.instance());
        }

        //if thirsty go and get a whiskey
        if (obj.isThirsty()) {
            obj.changeState(QuenchThirst.instance());
        }
    }
}
