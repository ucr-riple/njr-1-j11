package dnd.attributegui.races;

public class BaseRace {

    protected int[] _bonuses = {0, 0, 0, 0, 0, 0};

    public BaseRace(int i) {
        _bonuses[i] = 2;
    }

    public BaseRace(int i, int j) {
        _bonuses[i] = 2;
        _bonuses[j] = 2;
    }

    public int[] getBonuses() {
        return _bonuses;
    }

    public String getName() {
        return "Base Race";
    }

    public String getAttributeName(int i) {
        switch (i) {
            case 0:
                return "STR";
            case 1:
                return "DEX";
            case 2:
                return "CON";
            case 3:
                return "INT";
            case 4:
                return "WIS";
            case 5:
                return "CHA";
        }
        return "BAD";
    }
}
