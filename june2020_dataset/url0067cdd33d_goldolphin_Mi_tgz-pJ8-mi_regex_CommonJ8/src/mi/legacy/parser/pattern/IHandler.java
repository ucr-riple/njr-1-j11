package mi.legacy.parser.pattern;

/**
 * User: goldolphin
 * Time: 2013-05-29 20:18
 */
public interface IHandler<T> {
    void handle(int matchId, T ... terminals);
}
