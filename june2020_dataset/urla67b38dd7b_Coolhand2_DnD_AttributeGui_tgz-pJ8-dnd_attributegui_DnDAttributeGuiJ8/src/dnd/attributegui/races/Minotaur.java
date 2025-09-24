package dnd.attributegui.races;

public class Minotaur extends BaseRace {

    private int _i = 0;

    public Minotaur(int i) {
        super(0, i);
        _i = i;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Minotaur (" + super.getAttributeName(_i) + ")";
    }
}
