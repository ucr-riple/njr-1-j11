package dnd.attributegui.classes;

public class Battlemind extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {3, 4, 0, 5, 1, 2};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Battlemind";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {2, 4, 5};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getRole() {
        return "Defender";
    }
}
