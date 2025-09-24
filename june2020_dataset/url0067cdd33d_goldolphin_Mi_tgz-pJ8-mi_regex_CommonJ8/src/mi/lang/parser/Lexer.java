package mi.lang.parser;

import mi.common.CharType;
import mi.legacy.parser.character.IParseStream;
import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-07-13 16:07
 */
public class Lexer {
    private static boolean matchString(IParseStream stream, String s) {
        int pos = stream.tell();
        int len = s.length();
        for (int i = 0; i < len; i ++) {
            if (stream.poll() != s.charAt(i)) {
                stream.retractTo(pos);
                return false;
            }
        }
        return true;
    }

    public static void skipLineComment(IParseStream stream) {
        if (matchString(stream, "//")) {
            while (true) {
                char c = stream.peek();
                if (CharType.isNewLineChar(c) || c == ICharStream.EOF) {
                    break;
                }
                stream.poll();
            }
        }
    }

    public static void skipSpace(IParseStream stream) {
        while (true) {
            char c = stream.peek();
            if (CharType.isSpaceChar(c)) {
                break;
            }
            stream.poll();
        }
    }

    public static boolean matchInteger(IParseStream stream, StringBuilder buffer) {
        int pos = stream.tell();
        char c = stream.peek();
        int sign = 1;
        switch (c) {
            case '-':
                sign = -1;
            case '+':
                stream.poll();
        }
        return false;
    }
}
