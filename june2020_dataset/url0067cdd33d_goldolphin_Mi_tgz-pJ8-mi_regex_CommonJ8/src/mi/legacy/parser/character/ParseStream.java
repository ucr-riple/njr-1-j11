package mi.legacy.parser.character;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-07-05 15:09
 */
class ParseStream implements IParseStream {
    private ICharStream source;
    private int pos;
    private int lockPos;

    public ParseStream(ICharStream source) {
        this.source = source;
        this.pos = 0;
        lockPos = Integer.MIN_VALUE;
    }

    @Override
    public char peek() {
        return source.peek();
    }

    @Override
    public char poll() {
        pos ++;
        return source.poll();
    }

    @Override
    public void retract() {
        pos --;
        source.retract();
    }

    public void retractTo(int to) {
        for (int i = pos; i > to; i--) {
            retract();
        }
    }

    public int tell() {
        return pos;
    }
}
