package mi.regex;

import mi.common.CharacterSet;
import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-06-06 23:22
 */
public class SetRegex extends AtomRegex {
    private final boolean inclusive;
    private final CharacterSet set;
    private int count;


    public SetRegex(boolean inclusive) {
        this.inclusive = inclusive;
        set = new CharacterSet();
        count = 0;
    }

    public void add(char c) {
        if (!set.contains(c)) {
            set.add(c);
            count ++;
        }
    }

    public int count() {
        return count;
    }

    @Override
    void print(int indent) {
        describe(indent, (inclusive ? "inclusive" : "exclusive") + ", " + count);
        next.print(indent);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        char c = stream.peek();
        if (c != ICharStream.EOF && inclusive == set.contains(c)) {
            match.append(stream.poll());
            return next.match(stream, match);
        }
        return false;
    }
}
