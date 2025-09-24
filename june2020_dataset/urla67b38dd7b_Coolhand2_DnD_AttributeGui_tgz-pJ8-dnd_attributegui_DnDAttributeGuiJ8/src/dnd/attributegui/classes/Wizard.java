package dnd.attributegui.classes;

public class Wizard extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {4, 2, 3, 0, 1, 5};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Wizard";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {3, 4, 1};
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
