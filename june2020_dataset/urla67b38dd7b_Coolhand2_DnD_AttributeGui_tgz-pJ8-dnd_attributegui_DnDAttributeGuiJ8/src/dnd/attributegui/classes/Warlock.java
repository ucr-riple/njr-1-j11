package dnd.attributegui.classes;

public class Warlock extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {3, 4, 1, 2, 5, 0};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Warlock";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {5, 2, 3};
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
