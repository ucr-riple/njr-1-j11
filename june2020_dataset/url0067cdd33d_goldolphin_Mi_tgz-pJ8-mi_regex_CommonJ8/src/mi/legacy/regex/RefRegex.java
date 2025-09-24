package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:41
 */
public class RefRegex extends AtomRegex {
    private int n;

    public RefRegex(int n) {
        this.n = n;
    }

    @Override
    void print(int indent) {
        describe(indent, String.valueOf(n));
        next.print(indent);
    }

    @Override
    public boolean match(Match match, int offset) {
        int start = match.groupStart(n);
        int end = match.groupEnd(n);
        if (offset == start) {
            return next.match(match, end);
        }
        for (int i = start; i < end; i++, offset++) {
            if (match.end(i) || match.get(i) != match.get(offset)) {
                return false;
            }
        }
        return next.match(match, offset);
    }
}
