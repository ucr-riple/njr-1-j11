package mi.legacy.parser.character;

/**
 * User: goldolphin
 * Time: 2013-07-05 15:28
 */
class OrPattern implements IPattern {
    private final IPattern pattern1;
    private final IPattern pattern2;

    public OrPattern(IPattern pattern1, IPattern pattern2) {
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
    }

    @Override
    public ISymbol match(IParseStream stream) {
        ISymbol s = pattern1.match(stream);
        if (s != null) {
            return s;
        }
        return pattern2.match(stream);
    }
}
