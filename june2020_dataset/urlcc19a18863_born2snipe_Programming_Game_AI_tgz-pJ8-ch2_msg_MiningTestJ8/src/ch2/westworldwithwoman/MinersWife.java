package ch2.westworldwithwoman;

class MinersWife extends BaseGameEntity {
    public final StateMachine<MinersWife> machine;
    public Location location;

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

}
