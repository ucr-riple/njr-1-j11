package mi.lexer;

import java.util.HashMap;

/**
 * User: goldolphin
 * Time: 2013-05-22 23:32
 */
public enum TokenType {
    PACKAGE("package"),
    IMPORT("import"),

    LET("let"),
    TEMPLATE("template"),
    TYPE("type"),

    FUN("fun"),
    STRUCT("struct"),

    COLON(":"),
    SEMICOLON(";"),
    COMMA(","),
    LPAREN("("),
    RPAREN(")"),

    RARROW("=>"),
    ASSIGNMENT("="),
    LT("<"),
    GT(">"),

    ID(null),
    OPERATOR(null),
    INTEGER(null),
    DOUBLE(null),
    STRING(null),

    COMMENT(null),
    ;
    private static HashMap<String, TokenType> map = new HashMap<>();
    static {
        for (TokenType type: values()) {
            if (type.isPredefined()) {
                map.put(type.value, type);
            }
        }
    }

    public static TokenType of(String value) {
        return map.get(value);
    }

    public final String value;

    private TokenType(String value) {
        this.value = value;
    }

    public boolean isPredefined() {
        return value != null;
    }
}
