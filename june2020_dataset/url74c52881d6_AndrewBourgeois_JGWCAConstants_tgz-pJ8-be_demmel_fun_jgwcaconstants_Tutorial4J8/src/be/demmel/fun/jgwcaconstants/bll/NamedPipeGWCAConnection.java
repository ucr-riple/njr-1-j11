package be.demmel.fun.jgwcaconstants.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NamedPipeGWCAConnection implements GWCAConnection {

    private RandomAccessFile namedPipe;
    private final File namedPipeLocation;

    public NamedPipeGWCAConnection(File namedPipeLocation) {
        this.namedPipeLocation = namedPipeLocation;
    }
    
    public NamedPipeGWCAConnection(int pid) {
        this.namedPipeLocation = new File("\\\\.\\pipe\\GWCA_" + pid);
    }

    @Override
    public void sendPacket(GWCAPacket gwcaPacket) throws IOException {
        // create the message to send to the injected GWCA
        ByteBuffer byteBuffer = ByteBuffer.allocate(12);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        // first 2 bytes should represent 771 (CA_GetMyId) = header
        byteBuffer.putShort(gwcaPacket.getOperation().getOperationId());
        // next 2 bytes should represent 4 (?? BitOr of 2 enums, understant later...) = type
        byteBuffer.putShort(gwcaPacket.getOperationType());
        // next 4 bytes should represent 0 (no arg) = wparam
        byteBuffer.put(gwcaPacket.getWparam());
        // next 4 bytes should represent 0 (no arg) = lparam
        byteBuffer.put(gwcaPacket.getLparam());

        byte[] bytesToSend = byteBuffer.array();
        
        //TODO: make sure that all bytes are written
        this.namedPipe.write(bytesToSend);
    }

    @Override
    public GWCAPacket receivePacket() throws IOException {
        byte[] byteArrayBuffer = new byte[12];
        //TODO: make sure that all bytes are read (it's in blocking mode, so 1 byte is guaranteed to be read before it returns, but not more...)
        // read per byte if necessary, with a timeout!!
        this.namedPipe.read(byteArrayBuffer);

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayBuffer);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        short operationId = byteBuffer.getShort();
        short operationType = byteBuffer.getShort();
        byte[] lparam = new byte[4];
        byte[] wparam = new byte[4];

        byteBuffer.get(wparam);
        byteBuffer.get(lparam);
        
        return new GWCAPacket(GWCAOperation.toEnum(operationId), operationType, wparam, lparam);
    }

    @Override
    public void open() throws IOException {
        try {
            this.namedPipe = new RandomAccessFile(this.namedPipeLocation, "rw");
        } catch (FileNotFoundException ex) {
            // Convert the FNF
            throw new IOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void close() throws IOException {
        this.namedPipe.close();
    }
}
