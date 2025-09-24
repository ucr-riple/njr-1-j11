package mi.stack;

/**
 * @author goldolphin
 *         2014-05-12 23:34
 */
public class ArrayForkableStack<T> implements IForkableStack<T> {
    private final ArrayStack<T> dataBuffer;
    private final int[] parentBuffer;
    private int top;

    public ArrayForkableStack(ArrayStack<T> dataBuffer, int[] parentBuffer, int top) {
        this.dataBuffer = dataBuffer;
        this.parentBuffer = parentBuffer;
        this.top = top;
    }

    public ArrayForkableStack(int capacity) {
        this(new ArrayStack<T>(capacity), new int[capacity], -1);
    }

    @Override
    public T pop() {
        T data = dataBuffer.get(top);
        top = parentBuffer[top];
        return data;
    }

    @Override
    public void push(T data) {
        dataBuffer.push(data);
        int newTop = dataBuffer.size() - 1;
        parentBuffer[newTop] = top;
        top = newTop;
    }

    public ArrayForkableStack<T> fork() {
        return new ArrayForkableStack<>(dataBuffer, parentBuffer, top);
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void clear() {
        dataBuffer.clear();
        top = -1;
    }
}
