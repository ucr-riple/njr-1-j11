package dnd.attributegui.classes;

public class Druid extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {3, 1, 2, 4, 0, 5};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Druid";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {4, 1, 2};
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
