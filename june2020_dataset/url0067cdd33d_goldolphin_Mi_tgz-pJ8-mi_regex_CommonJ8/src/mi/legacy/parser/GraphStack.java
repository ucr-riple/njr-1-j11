package mi.legacy.parser;

/**
 * @author caofuxiang
 *         2014-02-13 14:23
 */
public class GraphStack<T> {
    private final GraphStack<T> last;
    private final T data;

    private GraphStack(GraphStack<T> last, T data) {
        this.last = last;
        this.data = data;
    }

    public GraphStack<T> push(T data) {
        return new GraphStack<>(this, data);
    }

    public GraphStack<T> pop() {
        return last;
    }

    public T getData() {
        return data;
    }

    public static <T> GraphStack<T> newBottom(T data) {
        return new GraphStack<>(null, data);
    }
}
