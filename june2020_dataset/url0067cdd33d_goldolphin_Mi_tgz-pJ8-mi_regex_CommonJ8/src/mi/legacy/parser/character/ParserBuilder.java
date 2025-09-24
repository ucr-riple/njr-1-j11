package mi.legacy.parser.character;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-07-08 00:19
 */
public class ParserBuilder {

    public static final ICombiner SELECT_FIRST = new ICombiner() {
        @Override
        public ISymbol combine(ISymbol t1, ISymbol t2) {
            return t1;
        }
    };

    public static final ICombiner SELECT_SECOND = new ICombiner() {
        @Override
        public ISymbol combine(ISymbol t1, ISymbol t2) {
            return t2;
        }
    };

    public static final ICombiner SELECT_NONE = new ICombiner() {
        @Override
        public ISymbol combine(ISymbol t1, ISymbol t2) {
            return ISymbol.EMPTY;
        }
    };

    public static IParseStream parseStream(ICharStream source) {
        return new ParseStream(source);
    }

    public static IPattern or(IPattern pattern1, IPattern pattern2) {
        return new OrPattern(pattern1, pattern2);
    }

    public static IPattern seq(IPattern pattern1, IPattern pattern2, ICombiner combiner) {
        return new SeqPattern(pattern1, pattern2, combiner);
    }

    public static IPattern star(IPattern prefix, IPattern pattern, ICombiner combiner) {
        return new SeqPattern(prefix, pattern, combiner);
    }

    public static IPattern leftRec(IPattern pattern) {
        return new LeftRec(pattern);
    }
}
