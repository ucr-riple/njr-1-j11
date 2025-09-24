package mi.legacy.regex;

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
    public boolean match(Match match, int offset) {
        if (left.match(match, offset)) {
            return true;
        }
        return right.match(match, offset);
    }
}
