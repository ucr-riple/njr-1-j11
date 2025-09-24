package hep.io.root.output;

import java.io.DataOutput;
import java.io.IOException;

/**
 * An output stream which is used to write root classes to a file.
 * @author tonyj
 */
public interface RootOutput extends  DataOutput {

    void writeObject(Object o) throws IOException;
    void writeObjectRef(Object o) throws IOException;
    /**
     * Test if pointers should be written as 32 or 64 bit integers.
     * @return <code>true</code> if should use 64 bit pointers.
     */
    boolean isLargeFile();
}
