package dnd.attributegui.classes;

public class Psion extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {5, 4, 3, 0, 2, 1};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Psion";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {3, 5, 4};
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
