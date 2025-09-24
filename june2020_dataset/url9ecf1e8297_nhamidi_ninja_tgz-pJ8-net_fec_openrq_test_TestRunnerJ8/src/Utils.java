import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static int RTT = 5000;
    public static int ACK = -2;
    public static int NACK = -1;
    public static int FLAG_PUSH = 999999999;
    public static int FLAG_STOP = 111111111;
    public static int SYMB_LENGTH = 192;
    public static double LOST = 0.1;
    public static int BIT_RATE = 26;
    public static int INLINE_REDON_RATE = 4;
    public static int IntegerSize = (Integer.SIZE / 8);
    public static float ALPHA = (float) 0.2;
    
    public static int byteArrayToInt(byte[] byte_array) {
	int integer = 0;
	for (int i = 0; i < IntegerSize; i++) {
	    integer = (integer << 8) + (byte_array[i] & 0xff);
	}
	return integer;
    }
    
    public static final byte[] intToByteArray(int value) {
	return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
    }
    
    public static void printInFile(String message, int port, int diff) {
	try {
	    String path = System.getProperty("user.dir");
	    File file = new File(path + "/histo/time_division_final_" + diff + "_" + port + ".txt");
	    
	    // if file doesnt exists, then create it
	    if ( !file.exists() ) {
		file.createNewFile();
	    }
	    
	    FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(message + "\n");
	    bw.close();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
}