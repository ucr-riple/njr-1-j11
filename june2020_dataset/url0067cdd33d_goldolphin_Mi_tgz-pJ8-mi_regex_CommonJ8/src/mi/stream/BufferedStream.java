package mi.stream;

/**
 * User: goldolphin
 * Time: 2013-05-28 21:20
 */
public class BufferedStream<T> implements IStream<T> {
    private final IStream<T> source;
    private final Object[] buffer;
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
    public BufferedStream(IStream<T> source, int bufferSize) {
        this.source = source;
        size = bufferSize;
        N = size + 1;
        buffer = new Object[N];
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
    public T peek() {
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
        return (T) buffer[pos];
    }

    @Override
    public T poll() {
        T t = peek();
        pos = move(pos, 1);
        return t;
    }

    @Override
    public void retract() {
        peek();
        pos = move(pos, -1);
    }
}
