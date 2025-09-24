package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-08 22:31
 */
public class GroupRegex extends AbstractRegex {
    private final int n;
    private final GroupEnd end;

    public GroupRegex(int n) {
        this.n = n;
        end = new GroupEnd(n);
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
        printChildren(indent, next);
    }

    @Override
    public boolean match(Match match, int offset) {
        match.setGroupStart(n, offset);
        return next.match(match, offset);
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
        public boolean match(Match match, int offset) {
            match.setGroupEnd(n, offset);
            return next.match(match, match.groupStart(n));
        }
    }
}
