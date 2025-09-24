package be.demmel.fun.jgwcaconstants.bll;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Represents a packet that will be sent to the DLL injected into the Guild Wars process
 * The structure of the packet is as follows:
 * - 2 unsigned bytes representing the operation to execute ==> short
 * - 2 unsigned bytes representing I don't know what ==> short
 * - 4 unsigned bytes representing the wparam (whatever that name means) => byte[4]
 * - 4 unsigned bytes representing the lparam (whatever that name means) => byte[4]
 */
public class GWCAPacket {
    private GWCAOperation operation;
    private short operationType;
    private final byte[] lparam, wparam;
    private static final byte[] ZERO = new byte[]{0,0,0,0};
    private static final short COMMANDS_BEGIN = 257, COMMANDS_END = 357, REQUEST_BEGIN = 769, REQUESTS_END = 943, COMMAND = 0x02, REQUEST = 0x04;

    // Used for outgoing packets: calculates the operationtype by itself
    //TODO: subclass this to make the difference clear
    public GWCAPacket(GWCAOperation operation, byte[] wparam, byte[] lparam) {
        this.operation = operation;
        
        //FIXME: we currently only support NUMERIC commands, so add support for TEXT
        // calculate whether this command is a REQUEST or a COMMAND
        short operationId = operation.getOperationId();
        if(operationId > COMMANDS_BEGIN && operationId < COMMANDS_END) {
            this.operationType = COMMAND;
        } else if(operationId > REQUEST_BEGIN && operationId < REQUESTS_END) {
            this.operationType = REQUEST;
        } else {
            // Cannot happen if the enum is currently coded
        }
        
        this.wparam = wparam;
        this.lparam = lparam;
    }
    
    // Used for outgoing packets: calculates the operationtype by itself and also converts the params to byte[4]
    //TODO: subclass this to make the difference clear
    public GWCAPacket(GWCAOperation operation, int wparam, int lparam) {
        this.operation = operation;
        
        //FIXME: we currently only support NUMERIC commands, so add support for TEXT
        // calculate whether this command is a REQUEST or a COMMAND
        short operationId = operation.getOperationId();
        if(operationId > COMMANDS_BEGIN && operationId < COMMANDS_END) {
            this.operationType = COMMAND;
        } else if(operationId > REQUEST_BEGIN && operationId < REQUESTS_END) {
            this.operationType = REQUEST;
        } else {
            // Cannot happen if the enum is currently coded
        }
        
        this.wparam = intToByteArray(wparam);
        this.lparam = intToByteArray(lparam);
    }
    
    
    
    public GWCAPacket(GWCAOperation operation, byte[] wparam) {
        this(operation, wparam, ZERO);
    }
    
    // Used for incoming packets: operationType must be 8
    //TODO: subclass this to make the difference clear
    public GWCAPacket(GWCAOperation operation, short operationType, byte[] wparam, byte[] lparam) {
        this.operation = operation;
        
        //FIXME: we currently only support NUMERIC commands, so add support for TEXT
        // calculate whether this command is a REQUEST or a COMMAND
        short operationId = operation.getOperationId();
        if(operationId > COMMANDS_BEGIN && operationId < COMMANDS_END) {
            this.operationType = COMMAND;
        } else if(operationId > REQUEST_BEGIN && operationId < REQUESTS_END) {
            this.operationType = REQUEST;
        } else {
            // Cannot happen if the enum is currently coded
        }
        
        this.operationType = operationType;
        this.wparam = wparam;
        this.lparam = lparam;
    }

    public byte[] getLparam() {
        return lparam;
    }
    
    public int getLparamAsInt() {
        return byteArrayToInt(this.lparam);
    }
    
    public int getWparamAsInt() {
        return byteArrayToInt(this.wparam);
    }
    
    public float getWparamAsFloat() {
        return byteArrayToFloat(this.wparam);
    }
    
    public boolean getWparamAsBoolean() {
        int intValue = byteArrayToInt(this.wparam);
        if(intValue == 1) {
            return true;
        } else if(intValue == 0) {
            return false;
        } else {
            throw new IllegalArgumentException("The wParam should be 0 or 1, but was ");
        }
    }
    
    public int[] getParamsAsIntArray() {
        return new int[]{byteArrayToInt(this.wparam), byteArrayToInt(this.lparam)};
    }
    
    public float[] getParamsAsFloatArray() {
        return new float[]{byteArrayToFloat(this.wparam), byteArrayToFloat(this.lparam)};
    }
    
    //TODO: better composite return for this
    public float[] getParamsAsIntFloatArray() {
        return new float[]{byteArrayToInt(this.wparam), byteArrayToFloat(this.lparam)};
    }

    public GWCAOperation getOperation() {
        return operation;
    }

    public short getOperationType() {
        return operationType;
    }

    public byte[] getWparam() {
        return wparam;
    }
    
    private int byteArrayToInt(byte[] b) {
        ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }
    
    private float byteArrayToFloat(byte[] b) {
        ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getFloat();
    }
    
    private byte[] intToByteArray(int i) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(i);
        return bb.array();
    }

    @Override
    public String toString() {
        return "GWCAPacket{" + "operation=" + operation + ", operationType=" + operationType + ", lparam=" + Arrays.toString(lparam) + ", wparam=" + Arrays.toString(wparam) + '}';
    }
}
