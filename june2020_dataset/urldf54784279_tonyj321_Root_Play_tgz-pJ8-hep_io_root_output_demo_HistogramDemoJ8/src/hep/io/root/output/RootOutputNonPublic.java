package hep.io.root.output;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author tonyj
 */
interface RootOutputNonPublic extends RootOutput, Closeable {

    void seek(long position) throws IOException;

    long getFilePointer() throws IOException;

    Map<String, Long> getClassMap();
    
    Map<String, TStreamerInfo> getStreamerInfos();
}
