package hep.io.root.output;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import hep.io.root.output.classes.TString;
import java.util.zip.Deflater;

/**
 *
 * @author tonyj
 */
class RootBufferedOutputStream extends DataOutputStream implements RootOutputNonPublic {

    private static final int kByteCountMask = 0x40000000;
    private static final int kNewClassTag = 0xFFFFFFFF;
    private static final int kClassMask = 0x80000000;
    private static final int kMapOffset = 2;
    private final RootByteArrayOutputStream buffer;
    private Map<String, Long> classMap = new HashMap<>();
    private int offset;
    private final TFile tFile;
    private boolean suppressStreamerInfo;

    RootBufferedOutputStream(TFile tFile, int offset, boolean suppressStreamerInfo) {
        this(tFile, new RootByteArrayOutputStream());
        this.offset = offset;
        this.suppressStreamerInfo = suppressStreamerInfo;
    }

    private RootBufferedOutputStream(TFile tFile, RootByteArrayOutputStream buffer) {
        super(buffer);
        this.buffer = buffer;
        this.tFile = tFile;
    }

    @Override
    public void writeObject(Object o) throws IOException {
        writeObject(this, o);
    }

    @Override
    public void writeObjectRef(Object o) throws IOException {
        writeObjectRef(this, o);
    }

    @Override
    public boolean isLargeFile() {
        return tFile.isLargeFile();
    }

    void writeTo(RootOutputNonPublic out, int compressionLevel) throws IOException {
        buffer.writeTo(out,compressionLevel);
    }
    
    int uncompressedSize() {
        return buffer.size();
    }
    @Override
    public void seek(long position) {
        buffer.seek(position - offset);
    }

    @Override
    public long getFilePointer() {
        return buffer.getFilePointer() + offset;
    }

    @Override
    public Map<String, Long> getClassMap() {
        return classMap;
    }

    @Override
    public Map<String, TStreamerInfo> getStreamerInfos() {
        return suppressStreamerInfo ? null : tFile.getStreamerInfos();
    }

    static void writeObject(RootOutputNonPublic out, Object o) throws IOException {
        if (o == null) {
            out.writeInt(0);
        } else {
            //FIXME: We should do something more general here
            if (o instanceof String) {
                o = new TString((String) o);
            }
            writeObject(out, o, o.getClass());
        }
    }

    static void writeObject(RootOutputNonPublic out, Object o, Class c) throws IOException {
        try {
            StreamerClassInfo classInfo = StreamerUtilities.getClassInfo(c);
            Map<String, TStreamerInfo> streamerInfos = out.getStreamerInfos();
            if (streamerInfos != null && !classInfo.suppressStreamerInfo() && !streamerInfos.containsKey(classInfo.getName())) {
                streamerInfos.put(classInfo.getName(), StreamerUtilities.getStreamerInfo(c));
            }
            try {
                Method m = c.getDeclaredMethod("streamer", RootOutput.class);
                m.setAccessible(true);
                m.invoke(o, out);
            } catch (NoSuchMethodException x) {
                if (classInfo.hasStandardHeader()) {
                    long objectPointer = out.getFilePointer();
                    out.writeInt(0); // space for length
                    out.writeShort(classInfo.getVersion());
                    Class sc = c.getSuperclass();
                    if (sc != Object.class) {
                        writeObject(out, o, sc);
                    }
                    try {
                        Method m = c.getDeclaredMethod("write", RootOutput.class);
                        m.setAccessible(true);
                        m.invoke(o, out);
                    } catch (NoSuchMethodException ex) {
                        classInfo.write(out, o);
                    }
                    long end = out.getFilePointer();
                    out.seek(objectPointer);
                    out.writeInt(kByteCountMask | (int) (end - objectPointer - 4));
                    out.seek(end);
                } else {
                    Class sc = c.getSuperclass();
                    if (sc != Object.class) {
                        writeObject(out, o, sc);
                    }
                    Method m = c.getDeclaredMethod("write", RootOutput.class);
                    m.setAccessible(true);
                    m.invoke(o, out);
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | StreamerInfoException ex) {
            throw new IOException("Problem writing object of class " + c.getName(), ex);
        }
    }

    static void writeObjectRef(RootOutputNonPublic out, Object o) throws IOException {
        if (o == null) {
            out.write(0);
        } else {
            writeObjectRef(out, o, o.getClass());
        }
    }

    static void writeObjectRef(RootOutputNonPublic out, Object o, Class c) throws IOException {

        long objectPointer = out.getFilePointer();
        out.writeInt(0); // Space for length
        StreamerClassInfo classInfo = StreamerUtilities.getClassInfo(c);
        String className = classInfo.getName();
        Long address = out.getClassMap().get(className);
        if (address == null) {
            address = out.getFilePointer();
            out.writeInt(kNewClassTag);
            out.write(className.getBytes());
            out.writeByte(0); // Null terminated
            out.getClassMap().put(className, address);
        } else {
            out.writeInt(kClassMask | (address.intValue() + kMapOffset));
        }
        writeObject(out, o, o.getClass());
        long end = out.getFilePointer();
        out.seek(objectPointer);
        out.writeInt(0x40000000 | (int) (end - objectPointer - 4));
        out.seek(end);
    }

    private static class RootByteArrayOutputStream extends ByteArrayOutputStream {

        private void writeTo(RootOutputNonPublic out, int compressionLevel) throws IOException {
            if (compressionLevel == 0 || count<200) {
                out.write(buf, 0, count);
            } else {
                out.writeByte('Z');
                out.writeByte('L');
                out.writeByte(8); // Method
                
                long compressedSizePos = out.getFilePointer();
                out.writeByte(0);
                out.writeByte(0);
                out.writeByte(0);

                out.writeByte(count & 0xff);
                out.writeByte((count>>8) & 0xff);
                out.writeByte((count>>16) & 0xff);

                Deflater deflater = new Deflater(compressionLevel,false);
                deflater.setInput(buf,0,count);
                deflater.finish();
                byte[] buffer = new byte[Math.min(32768, count)];
                while(!deflater.finished()) {
                    int l = deflater.deflate(buffer);
                    out.write(buffer,0,l);
                }
                deflater.end();
                long endPos = out.getFilePointer();
                int size = (int) (endPos-compressedSizePos-6);
                out.seek(compressedSizePos);
                out.writeByte(size & 0xff);
                out.writeByte((size>>8) & 0xff);
                out.writeByte((size>>16) & 0xff);
                out.seek(endPos);
            }
        }

        private long getFilePointer() {
            return count;
        }

        private void seek(long position) {
            count = (int) position;
        }
    }
}
