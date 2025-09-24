package mi.legacy.parser.character;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-07-07 22:49
 */
public interface IParseStream extends ICharStream {
    public void retractTo(int to);
    public int tell();
}
