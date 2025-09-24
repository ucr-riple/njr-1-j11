package ch2.westworld;

abstract class BaseGameEntity {
    private static int NEXT_ID = 0;
    private int id;

    protected BaseGameEntity() {
        id = ++NEXT_ID;
    }

    abstract void update();

    public int getId() {
        return id;
    }
}
