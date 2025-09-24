package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:41
 */
public class RefRegex extends AtomRegex {
    private int n;

    public RefRegex(int n) {
        this.n = n;
    }

    @Override
    void print(int indent) {
        describe(indent, String.valueOf(n));
        next.print(indent);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        int len = match.length();
        int start = match.groupStart(n);
        int end = match.groupEnd(n);
        if (len == start) {
            for (int i = start; i < end; i ++) {
                match.append(stream.poll());
            }
        } else {
            for (int i = start; i < end; i ++) {
                if (stream.peek() != match.get(i)) {
                    return false;
                }
                match.append(stream.poll());
            }
        }
        return next.match(stream, match);
    }
}
