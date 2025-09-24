package dnd.attributegui.classes;

public class Cleric extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {1, 4, 3, 5, 0, 2};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Cleric";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {4, 0, 5};
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
