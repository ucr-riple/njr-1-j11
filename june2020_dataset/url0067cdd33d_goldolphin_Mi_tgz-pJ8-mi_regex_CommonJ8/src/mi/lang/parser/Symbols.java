package mi.lang.parser;

/**
 * User: goldolphin
 * Time: 2013-07-08 02:46
 */
public class Symbols {
    public static class Id implements ISymbol {
        public final String name;

        public Id(String name) {
            this.name = name;
        }
    }
}
