package mi.stream;

/**
 * User: goldolphin
 * Time: 2013-06-01 11:11
 */
public interface ICharStream {
    public static final char EOF = Character.MAX_VALUE;

    public char peek();
    public char poll();
    public void retract();
}
