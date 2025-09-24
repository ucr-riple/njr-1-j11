package mi.stack;

/**
 * @author goldolphin
 *         2014-04-29 22:39
 */
public interface IStack<T> {
    public T pop();
    public void push(T data);
    public boolean isEmpty();
}
