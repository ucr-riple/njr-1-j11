package dnd.attributegui.classes;

public class Invoker extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {3, 4, 1, 2, 0, 5};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Invoker";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {4, 2, 3};
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
