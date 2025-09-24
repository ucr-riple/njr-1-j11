package mi.lexer;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-05-18 10:44
 */
public class Lexer {
    private static final char EOF = ICharStream.EOF;

    private LexStream stream;
    private Token token;
    private StringBuilder buffer = new StringBuilder();
    private int lineNum;
    private int colNum;

    public Lexer(LexStream reader) {
//        forwardInternal();
    }


    public Token peek() {
        return token;
    }

    public void forward() {
        if (token == null) {
            throw new LexException("End of file.");
        }
//        forwardInternal();
    }

    public Token poll() {
        Token t = peek();
        forward();
        return t;
    }
/*
    void forwardInternal() {
        char c = reader.peek();
        if (c == EOF) {
            token = null;
            return;
        }
        reader.forward();
        if (c == '/' && reader.peek() == '/') {
            forwardChar();
            consumeComment();
            forwardInternal();
        } else if (Symbols.isIdHead(c)) {
            //parseId();
        } else if (Symbols.isOperatorPart(c)) {
            //parseOperator();
        } else if (Symbols.isSpaceChar(c)) {
            forwardChar();
            forwardInternal();
        } else {
            //
        }

    }

    Token parseId(){
        char head = reader.peek();
        if (!Symbols.isIdHead(head)) {
            return null;
        }
        int lineNum = reader.getLineNum();
        int colNum = reader.getColNum();
        buffer.setLength(0);
        buffer.append(head);
        reader.forward();
        while (true) {
            char c = reader.peek();
            if (!Symbols.isIdTail(c)) {
                return Token.of(TokenType.Id, buffer.toString(), lineNum, colNum);
            }
            buffer.append(c);
            reader.forward();
        }
    }

    Token parseInteger() {
        char head = reader.peek();
        if (!Symbols.isDigit(head)) {
            return null;
        }
        int lineNum = reader.getLineNum();
        int colNum = reader.getColNum();
        buffer.setLength(0);
        buffer.append(head);
        reader.forward();
        while (true) {
            char c = reader.peek();
            if (!Symbols.isDigit(c)) {
                return Token.of(TokenType.in, buffer.toString(), lineNum, colNum);
            }
            buffer.append(c);
            reader.forward();
        }
    }

    void consumeComment() {
        char c = peekChar();
        if (c == EOF) {
            return;
        }
        if (Symbols.isNewLineChar(c)) {
            forwardChar();
            return;
        }
        consumeComment();
    }
    */
}
