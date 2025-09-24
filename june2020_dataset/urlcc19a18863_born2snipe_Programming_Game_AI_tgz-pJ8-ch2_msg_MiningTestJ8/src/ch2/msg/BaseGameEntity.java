package ch2.msg;

abstract class BaseGameEntity {
    private String id;

    protected BaseGameEntity(String id) {
        this.id = id;
    }

    abstract void update();

    public String getId() {
        return id;
    }

    abstract void handleMessage(Telegram telegram);
}
