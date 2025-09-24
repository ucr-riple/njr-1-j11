package dnd.attributegui.classes;

public class Paladin extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {0, 4, 3, 5, 2, 1};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Paladin";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {0, 5, 4};
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
