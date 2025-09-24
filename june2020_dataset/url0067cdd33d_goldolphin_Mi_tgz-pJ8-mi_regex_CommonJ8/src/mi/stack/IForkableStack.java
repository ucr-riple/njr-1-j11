package mi.stack;

/**
 * @author goldolphin
 *         2014-05-14 22:46
 */
public interface IForkableStack<T> extends IStack<T>, Forkable<IForkableStack<T>> {
}
