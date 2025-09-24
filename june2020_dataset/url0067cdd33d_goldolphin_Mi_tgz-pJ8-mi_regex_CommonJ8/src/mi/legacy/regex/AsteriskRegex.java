package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:48
 */
public class AsteriskRegex extends AbstractRegex {
    protected AtomRegex clause;

    public AsteriskRegex(AtomRegex clause) {
        this.clause = clause;
    }

    @Override
    void print(int indent) {
        describe(indent);
        printChildren(indent, clause);
        next.print(indent);
    }

    @Override
    public boolean match(Match match, int offset) {
        if (clause.match(match, offset) && match(match, match.newOffset())) {
            return true;
        }
        return next.match(match, offset);
    }
}
