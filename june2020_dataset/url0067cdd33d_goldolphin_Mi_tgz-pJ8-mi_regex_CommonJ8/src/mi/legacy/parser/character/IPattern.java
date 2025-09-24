package mi.legacy.parser.character;

/**
 * User: goldolphin
 * Time: 2013-07-03 23:12
 */
public interface IPattern {
    ISymbol match(IParseStream stream);
}
