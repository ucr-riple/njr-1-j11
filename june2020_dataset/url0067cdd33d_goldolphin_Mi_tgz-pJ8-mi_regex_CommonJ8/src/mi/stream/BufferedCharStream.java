package mi.stream;

/**
 * User: goldolphin
 * Time: 2013-06-01 11:12
 */
public class BufferedCharStream implements ICharStream {
    private final ICharStream source;
    private final char[] buffer;
    private final int size;
    private final int N;
    private int begin;
    private int end;
    private int pos;

    /**
     *
     * @param source need to at least implements poll()
     * @param bufferSize
     */
    public BufferedCharStream(ICharStream source, int bufferSize) {
        this.source = source;
        size = bufferSize;
        N = size + 1;
        buffer = new char[N];
        begin = 0;
        end = N - 1;
        pos = begin;
    }

    public final int move(int pos, int step) {
        return (pos + step + N) % N;
    }

    public final int length(int begin, int end) {
        return (end - begin + 1 + N) % N;
    }

    @Override
    public char peek() {
        if (length(pos, end) == 0) {
            if (length(begin, end) == size) {
                begin = move(begin, 1);
            }
            end = move(end, 1);
            buffer[end] = source.poll();
        }
        if (length(begin, pos) == 0) {
            while (length(begin, end) > 0) {
                end = move(end, -1);
                source.retract();
            }
            source.retract();
            begin = move(begin, -1);
            buffer[end] = source.poll();
        }
        return buffer[pos];
    }

    @Override
    public char poll() {
        char t = peek();
        pos = move(pos, 1);
        return t;
    }

    @Override
    public void retract() {
        peek();
        pos = move(pos, -1);
    }
}
