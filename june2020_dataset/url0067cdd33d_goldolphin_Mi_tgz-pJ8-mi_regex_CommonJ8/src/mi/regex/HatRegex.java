package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-09 18:06
 */
public class HatRegex extends AbstractRegex {
    @Override
    void print(int indent) {
        describe(indent);
        next.print(indent);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        if (match.length() == 0) {
            return next.match(stream, match);
        }
        return false;
    }
}
