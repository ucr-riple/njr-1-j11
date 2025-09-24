package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-08 02:50
 */
public class DotRegex extends AtomRegex {
    @Override
    void print(int indent) {
        describe(indent);
        next.print(indent);
    }

    @Override
    public boolean match(Match match, int offset) {
        if (match.end(offset)) {
            return false;
        }
        return next.match(match, offset+1);
    }
}
