package dnd.attributegui.classes;

public class Warlord extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {0, 4, 3, 1, 5, 2};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Warlord";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {0, 3, 5};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getRole() {
        return "Leader";
    }
}
