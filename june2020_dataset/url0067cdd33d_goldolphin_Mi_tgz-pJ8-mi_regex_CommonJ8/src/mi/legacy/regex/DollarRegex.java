package mi.legacy.regex;

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
    public boolean match(Match match, int offset) {
        if (match.end(offset)) {
            return next.match(match, offset);
        }
        return false;
    }
}
