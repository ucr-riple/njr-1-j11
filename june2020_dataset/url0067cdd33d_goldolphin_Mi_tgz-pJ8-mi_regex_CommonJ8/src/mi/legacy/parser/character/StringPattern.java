package mi.legacy.parser.character;

/**
 * User: goldolphin
 * Time: 2013-07-11 03:48
 */
public class StringPattern implements IPattern {
    private final String pattern;
    private final StringSymbol symbol;

    public StringPattern(String pattern) {
        this.pattern = pattern;
        symbol = new StringSymbol(pattern);
    }

    @Override
    public ISymbol match(IParseStream stream) {
        int pos = stream.tell();
        int len = pattern.length();
        for (int i = 0; i < len; i ++) {
            if (stream.poll() != pattern.charAt(i)) {
                stream.retractTo(pos);
                return null;
            }
        }
        return symbol;
    }
}
