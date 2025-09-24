package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-04 01:14
 */
public class OrRegex extends AbstractRegex {
    private AbstractRegex left;
    private AbstractRegex right;

    public OrRegex(AbstractRegex left, AbstractRegex right) {
        this.left = left;
        this.right = right;
    }

    @Override
    void print(int indent) {
        describe(indent);
        print(indent, "->");
        printChildren(indent, left);
        print(indent, "->");
        printChildren(indent, right);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        int len = match.length();
        if (left.match(stream, match)) {
            return true;
        }
        rollback(stream, match, len);
        return right.match(stream, match);
    }
}
