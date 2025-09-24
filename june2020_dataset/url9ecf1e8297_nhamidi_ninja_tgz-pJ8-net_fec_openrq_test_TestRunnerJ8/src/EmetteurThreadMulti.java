import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class EmetteurThreadMulti extends Thread {
    
    InetAddress groupeIP;
    private int port;
    private byte[] ACK_NACK;
    MulticastSocket socketEmission;
    
    EmetteurThreadMulti(InetAddress groupeIP, int port) throws Exception {
	this.groupeIP = groupeIP;
	this.port = port;
	this.ACK_NACK = new byte[Utils.IntegerSize];
	socketEmission = new MulticastSocket();
    }
    
    public void setAckType(int changeByte) {
	
	System.arraycopy(Utils.intToByteArray(changeByte), 0, this.ACK_NACK, 0, Utils.IntegerSize);
    }
    
    public void run() {
	try {
	    
	} catch (Exception exc) {
	    System.out.println(exc);
	}
    }
    
    void sendMsg() throws Exception {
	
	byte[] contenuMessage = new byte[Utils.IntegerSize];
	DatagramPacket message;
	System.arraycopy(this.ACK_NACK, 0, contenuMessage, 0, Utils.IntegerSize);
	message = new DatagramPacket(contenuMessage, contenuMessage.length, groupeIP, port);
	
	Thread.sleep(Utils.BIT_RATE);
	socketEmission.send(message);
    }
}
