package dnd.attributegui.races;

public class Wilden extends BaseRace {

    private int _i = 0;

    public Wilden(int i) {
        super(4, i);
        _i = i;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Wilden (" + super.getAttributeName(_i) + ")";
    }
}
