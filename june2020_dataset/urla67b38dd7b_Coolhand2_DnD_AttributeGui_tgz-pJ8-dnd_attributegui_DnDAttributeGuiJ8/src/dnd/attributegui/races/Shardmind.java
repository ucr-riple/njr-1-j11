package dnd.attributegui.races;

public class Shardmind extends BaseRace {

    private int _i = 0;

    public Shardmind(int i) {
        super(3, i);
        _i = i;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Shardmind (" + super.getAttributeName(_i) + ")";
    }
}
