package ch2.statemachine;

abstract class BaseGameEntity {
    private static int NEXT_ID = 0;
    private String id;

    protected BaseGameEntity(String id) {
        this.id = id;
    }

    abstract void update();

    public String getId() {
        return id;
    }
}
