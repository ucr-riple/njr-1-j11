package mi.lexer;

import java.util.HashMap;

/**
 * User: goldolphin
 * Time: 2013-05-19 16:12
 */
public class Token {
    public final TokenType type;
    public final String value;
    public final int lineNum;
    public final int colNum;

    private Token(TokenType type, String value, int lineNum, int colNum) {
        this.type = type;
        this.value = value;
        this.lineNum = lineNum;
        this.colNum = colNum;
    }

    public static Token of(TokenType suggestedType, String value, int lineNum, int colNum) {
        TokenType type = TokenType.of(value);
        if (type == null) {
            type = suggestedType;
        }
        return new Token(type, value, lineNum, colNum);
    }
}
