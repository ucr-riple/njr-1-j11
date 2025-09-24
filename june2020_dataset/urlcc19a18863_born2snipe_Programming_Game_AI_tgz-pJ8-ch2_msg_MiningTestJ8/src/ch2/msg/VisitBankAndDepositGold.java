package ch2.msg;

class VisitBankAndDepositGold implements State<Miner> {
    private static final int ComfortLevel = 5;

    private static final VisitBankAndDepositGold instance = new VisitBankAndDepositGold();

    public static VisitBankAndDepositGold instance() {
        return instance;
    }

    @Override
    public void enter(Miner obj) {
        //on entry the miner makes sure he is located at the bank
        if (obj.getLocation() != Location.BANK) {
            System.out.println(obj.getId() + ": Goin' to the bank. Yes siree");
            obj.changeLocation(Location.BANK);
        }
    }

    @Override
    public void exit(Miner obj) {
        System.out.println(obj.getId() + ": Leavin' the bank");
    }

    @Override
    public void execute(Miner obj) {
        //deposit the gold
        obj.depositGold();
        System.out.println(obj.getId() + ": Depositing gold. Total savings now: " + obj.getWealth());


        //wealthy enough to have a well earned rest?
        if (obj.getWealth() >= ComfortLevel) {
            System.out.println(obj.getId() + ": WooHoo! Rich enough for now. Back home to mah li'lle lady");
            obj.changeState(GoHomeAndSleepTilRested.instance());
        } else {
            //otherwise get more gold
            obj.changeState(EnterMineAndDigForNugget.instance());
        }
    }

    @Override
    public void onMessage(Miner obj, Telegram telegram) {
        
    }
}
