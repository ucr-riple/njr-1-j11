package dnd.attributegui.classes;

public class Ardent extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {3,4,1,5,2,0};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Ardent";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {5, 2, 4};
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
