package mi.legacy.regex;

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
    public boolean match(Match match, int offset) {
        if (match.begin(offset)) {
            return next.match(match, offset);
        }
        return false;
    }
}
