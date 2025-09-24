package mi.lexer;

import mi.stream.ICharStream;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Count lineNum & colNum, and support retract in each line.
 * User: goldolphin
 * Time: 2013-05-22 22:59
 */
public class LexStream implements ICharStream {

    private ICharStream source;
    private int lineNum;
    private int colNum;

    public LexStream(ICharStream source) {
        this.source = source;
        lineNum = 0;
        colNum = 0;
    }

    public char peek() {
        return source.peek();
    }

    public char poll() {
        char c = source.poll();
        if (c == '\n') {
            lineNum ++;
            colNum = 0;
        } else {
            colNum ++;
        }
        return c;
    }

    @Override
    public void retract() {
        if (colNum == 0) {
            throw new LexException("Retract cross lines.");
        }
        source.retract();
        colNum --;
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getColNum() {
        return colNum;
    }
}
