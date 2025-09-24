package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:55
 */
public class PlusRegex extends AsteriskRegex {

    public PlusRegex(AtomRegex clause) {
        super(clause);
    }

    @Override
    public boolean match(Match match, int offset) {
        if (clause.match(match, offset)) {
            return super.match(match, match.newOffset());
        }
        return false;
    }
}
