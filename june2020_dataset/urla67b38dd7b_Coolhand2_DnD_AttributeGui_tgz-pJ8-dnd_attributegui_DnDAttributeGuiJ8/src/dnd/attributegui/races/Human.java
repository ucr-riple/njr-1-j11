package dnd.attributegui.races;

public class Human extends BaseRace {

    private int _i = 0;

    public Human(int i) {
        super(i);
        _i = i;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Human (" + super.getAttributeName(_i) + ")";
    }
}
