package ch2.westworldwithwoman;

class Miner extends BaseGameEntity {
    private static final int MaxNuggets = 3;
    private static final int ThirstLevel = 5;
    private static final int TirednessThreshold = 5;
    private StateMachine<Miner> machine;

    private Location location;
    private int goldCarried;
    private int moneyInTheBank;
    private int thirst;
    private int fatigue;

    public Miner() {
        super("Miner Bob");
        machine = new StateMachine<Miner>(this);
        machine.setCurrentState(GoHomeAndSleepTilRested.instance());
    }


    @Override
    void update() {
        thirst++;
        machine.update();
    }

    void changeState(State state) {
        machine.changeState(state);
    }

    Location getLocation() {
        return location;
    }

    void changeLocation(Location location) {
        this.location = location;
    }

    void addToGoldCarried(int i) {
        goldCarried += i;
    }

    void increaseFatigue() {
        fatigue++;
    }

    boolean arePocketsFull() {
        return goldCarried >= MaxNuggets;
    }

    boolean isThirsty() {
        return thirst >= ThirstLevel;
    }

    void depositGold() {
        moneyInTheBank += goldCarried;
        goldCarried = 0;
    }

    int getWealth() {
        return moneyInTheBank;
    }

    void sleep() {
        fatigue--;
    }

    boolean isFatigued() {
        return fatigue >= TirednessThreshold;
    }

    void buyAndDrinkAWhiskey() {
        thirst = 0;
        moneyInTheBank -= 2;
    }
}
