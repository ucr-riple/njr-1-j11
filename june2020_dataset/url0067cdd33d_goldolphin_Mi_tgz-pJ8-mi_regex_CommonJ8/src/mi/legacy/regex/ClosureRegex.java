package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:23
 */
public class ClosureRegex extends AbstractRegex {
    private AtomRegex clause;
    private int lower;
    private int upper;

    public ClosureRegex(AtomRegex clause, int lower, int upper) {
        this.clause = clause;
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    void print(int indent) {
        describe(indent, String.format("%d, %d", lower, upper));
        printChildren(indent, clause);
        next.print(indent);
    }

    private boolean match(Match match, int offset, int n) {
        if (n > upper) {
            return false;
        }
        if (clause.match(match, offset) && match(match, match.newOffset(), n+1)) {
            return true;
        }
        if (n < lower) {
            return false;
        }
        return next.match(match, offset);
    }

    @Override
    public boolean match(Match match, int offset) {
        return match(match, offset, 0);
    }
}
