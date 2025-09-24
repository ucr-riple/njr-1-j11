package dnd.attributegui.classes;

public class BaseClass {

    protected int[] _ranks = {0, 0, 0, 0, 0, 0};
    protected int[] _preferred = {0, 1, 2};

    public BaseClass(){

    }

    public int[] getRanks() {
        return _ranks;
    }

    public String getName() {
        return "BaseClass";
    }

    public int[] getPreferred() {
        return _preferred;
    }

    public String getRole() {
        return "Role";
    }
}
