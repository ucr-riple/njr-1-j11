package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:42
 */
public class QuestionRegex extends AbstractRegex {
    private AtomRegex clause;

    public QuestionRegex(AtomRegex clause) {
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
        if (clause.match(match, offset) && next.match(match, match.newOffset())) {
            return true;
        }
        return next.match(match, offset);
    }
}
