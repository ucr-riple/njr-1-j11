package mi.common;

/**
 * User: goldolphin
 * Time: 2013-07-05 16:57
 */
public class IntStack {
    private final int[] buffer;
    private int size;

    public IntStack(int capacity) {
        buffer = new int[capacity];
        size = 0;
    }

    public int pop() {
        size --;
        return peek();
    }

    public int peek() {
        return buffer[size-1];
    }

    public void push(int d) {
        buffer[size++] = d;
    }
}
