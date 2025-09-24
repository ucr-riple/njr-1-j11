package dnd.attributegui.classes;

public class Sorcerer extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {2, 1, 3, 4, 5, 0};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Sorcerer";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {5, 1, 0};
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
