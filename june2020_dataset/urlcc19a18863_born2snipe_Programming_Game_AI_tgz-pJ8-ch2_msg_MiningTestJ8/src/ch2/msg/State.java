package ch2.msg;

interface State<T> {
    void enter(T obj);
    void execute(T obj);
    void exit(T obj);
    void onMessage(T obj, Telegram telegram);
}
