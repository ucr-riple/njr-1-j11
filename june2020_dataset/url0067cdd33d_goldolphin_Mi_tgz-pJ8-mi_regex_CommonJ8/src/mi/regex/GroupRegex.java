package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-08 22:31
 */
public class GroupRegex extends AbstractRegex {
    private final int n;
    private AbstractRegex clause;
    private GroupEnd end;

    public GroupRegex(int n) {
        this.n = n;
        end = new GroupEnd(n);
    }

    public void setClause(AbstractRegex clause) {
        this.clause = clause;
    }

    @Override
    protected void setNext(AbstractRegex next) {
        end.setNext(next);
    }

    public AbstractRegex groupEnd() {
        return end;
    }

    public int groupNum() {
        return n;
    }

    @Override
    void print(int indent) {
        describe(indent, String.valueOf(n));
        printChildren(indent, clause);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        match.setGroupStart(n, match.length());
        return clause.match(stream, match);
    }

    private static class GroupEnd extends AbstractRegex {
        private int n;

        private GroupEnd(int n) {
            this.n = n;
        }

        @Override
        void print(int indent) {
            describe(indent, String.valueOf(n));
            next.print(indent);
        }

        @Override
        public boolean match(ICharStream stream, Match match) {
            match.setGroupEnd(n, match.length());
            return next.match(stream, match);
        }
    }
}
