package ch2.statemachine;

public interface State<T> {
    void enter(T obj);
    void execute(T obj);
    void exit(T obj);
}
