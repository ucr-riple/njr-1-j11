package dnd.attributegui.classes;

public class Avenger extends BaseClass {

    /**
     *
     * @return
     */
    @Override
    public int[] getRanks() {
        int[] local = {4,1,3,2,0,5};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "Avenger";
    }

    /**
     *
     * @return
     */
    @Override
    public int[] getPreferred() {
        int[] local = {4, 1, 3};
        return local;
    }

    /**
     *
     * @return
     */
    @Override
    public String getRole() {
        return "Striker";
    }
}
