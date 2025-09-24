package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:04
 */
public class CharRegex extends AtomRegex {
    private char c;

    public CharRegex(char c) {
        this.c = c;
    }

    @Override
    void print(int indent) {
        describe(indent, String.valueOf(c));
        next.print(indent);
    }

    @Override
    public boolean match(Match match, int offset) {
        if (match.end(offset)) {
            return false;
        }
        if (match.get(offset) == c) {
            return next.match(match, offset+1);
        }
        return false;
    }
}
