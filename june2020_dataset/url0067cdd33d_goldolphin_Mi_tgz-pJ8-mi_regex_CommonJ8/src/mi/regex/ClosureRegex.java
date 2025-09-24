package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:23
 */
public class ClosureRegex extends AbstractRegex {
    private static final int MAX_UPPER = Integer.MAX_VALUE;
    private AbstractRegex clause;
    private Loop loop;

    public ClosureRegex(AbstractRegex clause, int lower, int upper) {
        this.clause = clause;
        loop = new Loop(clause, lower, upper);
        clause.setNext(loop);
    }

    public ClosureRegex(AbstractRegex clause, int lower) {
        this(clause, lower, MAX_UPPER);
    }

    @Override
    protected void setNext(AbstractRegex next) {
        loop.setNext(next);
    }

    @Override
    void print(int indent) {
        describe(indent, String.format("%d, %s", loop.lower, loop.upper == MAX_UPPER ? "MAX" : loop.upper));
        printChildren(indent, clause);
        loop.next.print(indent);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        loop.reset();
        return clause.match(stream, match);
    }

    private static class Loop extends AbstractRegex {
        private final AbstractRegex clause;
        private final int lower;
        private final int upper;
        private int repeated;

        private Loop(AbstractRegex clause, int lower, int upper) {
            this.clause = clause;
            this.lower = lower;
            this.upper = upper;
            this.repeated = 0;
        }

        @Override
        void print(int indent) {
        }

        void reset() {
            repeated = 0;
        }

        @Override
        public boolean match(ICharStream stream, Match match) {
            repeated ++;
            if (repeated > upper) {
                return false;
            }
            if (repeated == upper) {
                return next.match(stream, match);
            }

            int len = match.length();
            int r = repeated;
            if (clause.match(stream, match)) {
                return true;
            }
            if (repeated < lower) {
                return false;
            }
            rollback(stream, match, len);
            repeated = r;
            return next.match(stream, match);
        }
    }
}
