package ch2.msg;

class MinersWife extends BaseGameEntity {
    public final StateMachine<MinersWife> machine;
    public Location location;
    private boolean cooking;

    public MinersWife() {
        super("Elsa");
        machine = new StateMachine<MinersWife>(this);
        machine.setGlobalState(WifesGlobalState.instance());
        machine.setCurrentState(DoHouseWork.instance());
        location = Location.SHACK;
    }

    @Override
    void update() {
        machine.update();
    }

    @Override
    void handleMessage(Telegram telegram) {
        machine.handleMessage(telegram);
    }

    boolean isCooking() {
        return cooking;
    }

    void setCooking(boolean cooking) {
        this.cooking = cooking;
    }

}
