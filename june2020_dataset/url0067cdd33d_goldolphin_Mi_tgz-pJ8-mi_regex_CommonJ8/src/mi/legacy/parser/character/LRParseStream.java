package mi.legacy.parser.character;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-07-07 22:39
 */
class LRParseStream implements IParseStream {
    private static final IParseStream FAKE_STREAM = new IParseStream() {
        @Override
        public char peek() {
            return ICharStream.EOF;
        }

        @Override
        public char poll() {
            return ICharStream.EOF;
        }

        @Override
        public void retract() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int tell() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void retractTo(int to) {
            throw new UnsupportedOperationException();
        }
    };

    private final IParseStream source;
    private IParseStream stream;
    private int lockPos;

    public LRParseStream(IParseStream source) {
        this.source = source;
        this.stream = source;
        lockPos = Integer.MIN_VALUE;
    }

    @Override
    public char peek() {
        return stream.peek();
    }

    @Override
    public char poll() {
        return stream.poll();
    }

    @Override
    public void retract() {
        stream.retract();
        if (tell() == lockPos) {
            stream = FAKE_STREAM;
        }
    }

    public void retractTo(int to) {
        for (int i = stream.tell(); i > to; i--) {
            retract();
        }
    }

    public int tell() {
        return stream.tell();
    }

    public void lock() {
        lockPos = tell();
        stream = FAKE_STREAM;
    }

    public void yield() {
        stream = source;
    }
}
