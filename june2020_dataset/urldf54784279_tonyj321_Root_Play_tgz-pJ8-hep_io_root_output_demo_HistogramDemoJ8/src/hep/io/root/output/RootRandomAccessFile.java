package hep.io.root.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tonyj
 */
class RootRandomAccessFile extends RandomAccessFile implements RootOutputNonPublic {

    private final TFile tFile;
    private Map<String, Long> classMap = new HashMap<>();

    RootRandomAccessFile(File file, TFile tFile) throws FileNotFoundException {
        super(file, "rw");
        this.tFile = tFile;
    }

    @Override
    public void writeObject(Object o) throws IOException {
        RootBufferedOutputStream.writeObject(this, o);
    }

    @Override
    public boolean isLargeFile() {
        return tFile.isLargeFile();
    }

    @Override
    public void writeObjectRef(Object o) throws IOException {
        RootBufferedOutputStream.writeObjectRef(this, o);
    }

    @Override
    public Map<String, Long> getClassMap() {
        return classMap;
    }

    @Override
    public Map<String, TStreamerInfo> getStreamerInfos() {
        return tFile.getStreamerInfos();
    }
}
