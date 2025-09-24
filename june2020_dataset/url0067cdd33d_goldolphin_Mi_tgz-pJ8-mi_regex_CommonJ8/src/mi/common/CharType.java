package mi.common;

/**
 * User: goldolphin
 * Time: 2013-05-20 20:29
 */
public enum CharType {
    DIGIT,
    UPPERCASE,
    LOWERCASE,
    OTHER,
    ;

    public static CharType getType(char c) {
        if (isDigit(c)) {
            return DIGIT;
        }
        if (isUpperCase(c)) {
            return UPPERCASE;
        }
        if (isLowerCase(c)) {
            return LOWERCASE;
        }
        return OTHER;
    }

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isLetter(char c) {
        return isUpperCase(c) || isLowerCase(c);
    }

    public static boolean isIdHead(char c) {
        return isLetter(c) || c == '_';
    }

    public static boolean isIdTail(char c) {
        return isIdHead(c) || isDigit(c);
    }

    private static final CharacterSet OPERATOR_CHARS = new CharacterSet();
    private static final CharacterSet CONTROL_CHARS = new CharacterSet();

    static {
        for (char c: "|&^!=<>+-*/%~".toCharArray()) {
            OPERATOR_CHARS.add(c);
        }

        for (char c: "\r\n\f".toCharArray()) {
            CONTROL_CHARS.add(c);
        }
    }


    public static boolean isOperatorPart(char c) {
        return OPERATOR_CHARS.contains(c);
    }

    public static boolean isNewLineChar(char c){
        return c == '\n';
    }

    public static boolean isSpaceChar(char c) {
        return c == ' ' || c == '\t';
    }

    public static boolean isBlankChar(char c) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

    public static void main(String[] args) {
        System.out.println(Character.isISOControl(' '));
    }
}
