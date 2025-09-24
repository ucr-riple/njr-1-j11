
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Emetteur_thread extends Thread {
	InetAddress  groupeIP;
	int port;
	private byte ACK_NACK;
	DatagramSocket socketEmission;
  
	Emetteur_thread(InetAddress groupeIP, int port) throws Exception {
			this.groupeIP = groupeIP;
			this.port = port;
			this.ACK_NACK = (byte)0;
			socketEmission = new DatagramSocket();
	}
	
	public byte get_ACK_NACK(){
			return ACK_NACK;
	}
	public void set_ACK_NACK(byte change_byte){
			ACK_NACK=change_byte;  
	}	
	
	public void run() {
	try {
		send_thing();
		}
	catch (Exception exc) {
		System.out.println(exc);
		}
	} 

	void send_thing() throws Exception {
		byte[] contenuMessage=new byte[256];
		DatagramPacket message;
		contenuMessage[0]=ACK_NACK;
		message = new DatagramPacket(contenuMessage, contenuMessage.length, groupeIP, port);
		socketEmission.send(message);
	}
}