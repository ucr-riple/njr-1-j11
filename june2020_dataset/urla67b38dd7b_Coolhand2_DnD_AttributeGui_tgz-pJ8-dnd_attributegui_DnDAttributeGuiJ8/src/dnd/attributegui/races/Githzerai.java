package dnd.attributegui.races;

public class Githzerai extends BaseRace {

    private int _i = 0;

    public Githzerai(int i) {
        super(4, i);
        _i = i;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Githzerai (" + super.getAttributeName(_i) + ")";
    }
}
