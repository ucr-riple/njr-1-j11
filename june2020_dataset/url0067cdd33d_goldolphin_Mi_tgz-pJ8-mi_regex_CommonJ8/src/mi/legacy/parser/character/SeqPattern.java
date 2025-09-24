package mi.legacy.parser.character;

/**
 * User: goldolphin
 * Time: 2013-07-05 15:00
 */
class SeqPattern implements IPattern {
    private final IPattern pattern1;
    private final IPattern pattern2;
    private final ICombiner combiner;

    public SeqPattern(IPattern pattern1, IPattern pattern2, ICombiner combiner) {
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
        this.combiner = combiner;
    }

    @Override
    public ISymbol match(IParseStream stream) {
        int pos = stream.tell();
        ISymbol s1 = pattern1.match(stream);
        if (s1 != null) {
            ISymbol s2 = pattern2.match(stream);
            if (s2 != null) {
                return combiner.combine(s1, s2);
            }
            stream.retractTo(pos);
        }
        return null;
    }
}
