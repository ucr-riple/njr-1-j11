package dnd.attributegui.classes;

public class Seeker extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {1, 2, 3, 4, 0, 5};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Seeker";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {4, 0, 1};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getRole() {
        return "Controller";
    }
}
