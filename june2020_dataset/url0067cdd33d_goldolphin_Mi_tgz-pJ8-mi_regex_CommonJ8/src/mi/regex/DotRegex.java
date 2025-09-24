package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-08 02:50
 */
public class DotRegex extends AtomRegex {
    @Override
    void print(int indent) {
        describe(indent);
        next.print(indent);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        char c = stream.peek();
        if (c != ICharStream.EOF) {
            match.append(stream.poll());
            return next.match(stream, match);
        }
        return false;
    }
}
