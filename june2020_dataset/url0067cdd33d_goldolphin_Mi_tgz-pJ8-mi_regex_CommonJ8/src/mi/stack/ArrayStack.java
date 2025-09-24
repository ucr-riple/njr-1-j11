package mi.stack;

/**
 * @author goldolphin
 *         2014-05-12 23:09
 */
public class ArrayStack<T> implements IRandomAccessStack<T> {
    private final T[] buffer;
    private int size = 0;

    public ArrayStack(int capacity) {
        this.buffer = (T[]) new Object[capacity];
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return buffer[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T pop() {
        T data = buffer[size-1];
        buffer[size-1] = null;
        size -= 1;
        return data;
    }

    @Override
    public void push(T data) {
        buffer[size] = data;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        while (!isEmpty()) {
            pop();
        }
    }

    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }
}
