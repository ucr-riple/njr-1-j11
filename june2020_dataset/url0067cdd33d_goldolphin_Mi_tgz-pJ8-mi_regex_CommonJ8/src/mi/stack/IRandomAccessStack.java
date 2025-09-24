package mi.stack;

/**
 * @author goldolphin
 *         2014-05-12 23:10
 */
public interface IRandomAccessStack<T> extends IStack<T> {
    public T get(int index);
    public int size();
}
