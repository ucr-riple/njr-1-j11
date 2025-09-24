package dnd.attributegui.classes;

public class Rogue extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {1, 0, 3, 4, 5, 2};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Rogue";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {1, 0, 5};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getRole() {
        return "Striker";
    }
}
