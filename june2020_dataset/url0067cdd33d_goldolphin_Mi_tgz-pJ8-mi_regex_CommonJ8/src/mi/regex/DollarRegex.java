package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-09 18:08
 */
public class DollarRegex extends AbstractRegex {
    @Override
    void print(int indent) {
        describe(indent);
        next.print(indent);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        if (stream.peek() == ICharStream.EOF) {
            return next.match(stream, match);
        }
        return false;
    }
}
