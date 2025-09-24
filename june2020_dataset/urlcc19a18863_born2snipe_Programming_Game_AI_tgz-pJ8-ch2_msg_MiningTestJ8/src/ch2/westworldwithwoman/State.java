package ch2.westworldwithwoman;

public interface State<T> {
    void enter(T obj);
    void execute(T obj);
    void exit(T obj);
}
