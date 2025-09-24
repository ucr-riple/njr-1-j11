package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:15
 */
public abstract class AbstractRegex {
    protected AbstractRegex next;

    protected void setNext(AbstractRegex next) {
        this.next = next;
    }

    abstract void print(int indent);

    protected static void print(int indent, String content) {
        for (int i = 0; i < indent; i++) {
            System.out.print(' ');
        }
        System.out.println(content);
    }

    protected void describe(int indent) {
        print(indent, getClass().getSimpleName());
    }

    protected void describe(int indent, String detail) {
        print(indent, getClass().getSimpleName() + "(" + detail + ")");
    }

    protected static void printChildren(int indent, AbstractRegex child) {
        child.print(indent + 4);
    }

    public abstract boolean match(Match match, int offset);
}
