package mi.stream;

import java.io.*;

/**
 * User: goldolphin
 * Time: 2013-06-05 23:15
 */
public class ISStream implements ICharStream {
    private Reader reader;

    public ISStream(InputStream source) {
        reader = new InputStreamReader(source);
    }

    public ISStream(InputStream source, String charsetName) throws UnsupportedEncodingException {
        reader = new InputStreamReader(source, charsetName);
    }

    @Override
    public char peek() {
        throw new UnsupportedOperationException();
    }

    @Override
    public char poll() {
        try {
            int c = reader.read();
            if (c == -1) {
                return EOF;
            }
            return (char) c;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void retract() {
        throw new UnsupportedOperationException();
    }
}
