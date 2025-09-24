package ch2.westworld;

interface State<T> {

    void enter(T obj);

    void exit(T obj);

    void execute(T obj);
}
