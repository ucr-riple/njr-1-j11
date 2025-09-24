package ccproxy;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NullChannelWriter extends ChannelWriter{
    public NullChannelWriter() {
        super(null,null);
        b=null;
    }

    @Override
    public void println(String s) {
    }

    @Override
    public void close() {
    }
}
