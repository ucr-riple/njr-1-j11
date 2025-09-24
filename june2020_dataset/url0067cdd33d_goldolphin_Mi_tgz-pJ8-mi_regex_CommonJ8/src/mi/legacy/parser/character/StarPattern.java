package mi.legacy.parser.character;

/**
 * User: goldolphin
 * Time: 2013-07-07 23:52
 */
class StarPattern implements IPattern {
    private final IPattern prefix;
    private final IPattern pattern;
    private final ICombiner combiner;

    /**
     *
     * @param prefix null for an empty prefix
     * @param pattern
     * @param combiner
     */
    public StarPattern(IPattern prefix, IPattern pattern, ICombiner combiner) {
        this.prefix = prefix;
        this.pattern = pattern;
        this.combiner = combiner;
    }

    @Override
    public ISymbol match(IParseStream stream) {
        ISymbol result = null;
        if (prefix != null) {
            result = prefix.match(stream);
            if (result == null) {
                return null;
            }
        }
        while (true) {
            ISymbol s = pattern.match(stream);
            if (s == null) {
                return result;
            }
            result = combiner.combine(result, s);
        }
    }
}
