package dnd.attributegui.classes;

public class Fighter extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {0, 1, 3, 4, 2, 5};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Fighter";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {0, 1, 4};
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
