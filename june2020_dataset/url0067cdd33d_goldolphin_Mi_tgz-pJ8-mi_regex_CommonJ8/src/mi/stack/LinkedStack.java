package mi.stack;

/**
 * @author goldolphin
 *         2014-05-14 23:01
 */
public class LinkedStack<T> implements IForkableStack<T> {
    private Entry<T> top;

    private LinkedStack(Entry<T> top) {
        this.top = top;
    }

    public LinkedStack() {
        this(null);
    }

    @Override
    public LinkedStack<T> fork() {
        return new LinkedStack<>(top);
    }

    @Override
    public T pop() {
        T data = top.data;
        top = top.parent;
        return data;
    }

    @Override
    public void push(T data) {
        top = new Entry<>(data, top);
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    private static class Entry<T> {
        final T data;
        final Entry<T> parent;

        private Entry(T data, Entry<T> parent) {
            this.data = data;
            this.parent = parent;
        }
    }
}
