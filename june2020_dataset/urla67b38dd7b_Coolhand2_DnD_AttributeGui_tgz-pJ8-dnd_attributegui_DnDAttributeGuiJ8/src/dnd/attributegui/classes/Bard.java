package dnd.attributegui.classes;

public class Bard extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {3, 4, 2, 1, 5, 0};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Bard";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {5, 3, 2};
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
