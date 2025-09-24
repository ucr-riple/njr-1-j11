package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:04
 */
public class CharRegex extends AtomRegex {
    private char ch;

    public CharRegex(char ch) {
        this.ch = ch;
    }

    @Override
    void print(int indent) {
        describe(indent, String.valueOf(ch));
        next.print(indent);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        char c = stream.peek();
        if (c == ch) {
            match.append(stream.poll());
            return next.match(stream, match);
        }
        return false;
    }
}
