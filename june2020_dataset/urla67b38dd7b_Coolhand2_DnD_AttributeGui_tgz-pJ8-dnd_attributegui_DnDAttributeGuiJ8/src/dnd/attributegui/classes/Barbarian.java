package dnd.attributegui.classes;

public class Barbarian extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {0, 3, 1, 4, 5, 2};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Barbarian";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {0, 2, 5};
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
