package mi.lang.parser;

import mi.common.CharType;
import mi.legacy.parser.character.IParseStream;
import mi.legacy.parser.character.ParserBuilder;
import mi.legacy.parser.pattern.IPattern;

/**
 * User: goldolphin
 * Time: 2013-07-08 02:31
 */
public class Parser {
/*
    private ParserBuilder<ISymbol> builder = new ParserBuilder<>();

    IPattern<ISymbol> Id = new IPattern<ISymbol>() {
        private StringBuilder buffer = new StringBuilder();

        @Override
        public ISymbol match(IParseStream stream) {
            char c = stream.peek();
            if (!CharType.isIdHead(c)) {
                return null;
            }
            buffer.setLength(0);
            buffer.append(c);
            while (true) {
                c = stream.peek();
                if (!CharType.isIdTail(c)) {
                    break;
                }
                buffer.append(c);
                stream.poll();
            }
            return new Symbols.Id(buffer.toString());
        }
    };

    IPattern<ISymbol> Operator = new IPattern<ISymbol>() {
        private StringBuilder buffer = new StringBuilder();

        @Override
        public ISymbol match(IParseStream stream) {
            buffer.setLength(0);
            while (true) {
                char c = stream.peek();
                if (!CharType.isIdTail(c)) {
                    break;
                }
                buffer.append(c);
                stream.poll();
            }
            return new Symbols.Id(buffer.toString());
        }
    };
*/
}
