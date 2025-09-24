package mi.stream;

/**
 * Implementation need to provide an EOF item to identify the end-of-stream.
 * User: goldolphin
 * Time: 2013-05-28 21:06
 */
public interface IStream<T> {
    public T peek();
    public T poll();
    public void retract();
}
