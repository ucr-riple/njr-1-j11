package mi.lexer;

import mi.regex.Regex;
import mi.stream.ICharStream;
import mi.stream.IStream;

/**
 * Only support poll.
 * User: goldolphin
 * Time: 2013-06-10 19:50
 */
public class TokenStream implements IStream<Token> {
    private final LexStream source;

    public TokenStream(ICharStream source) {
        this.source = new LexStream(source);
    }

    @Override
    public Token peek() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Token poll() {

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void retract() {
        throw new UnsupportedOperationException();
    }

    private static final Regex COMMENT_REGEX = new Regex("//[^\n]*");
    private static final Regex OPERATOR_REGEX = new Regex("[|&^!=<>+-*/%~]+");
    private static final Regex ID_REGEX = new Regex("[_A-Za-z][_A-Za-z0-9]*");

    Token parse() {
        char c = source.peek();
        return null;
    }
}
