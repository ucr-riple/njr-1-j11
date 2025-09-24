
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Recepteur_thread extends Thread {
	private int port;
	private DatagramSocket socketReception;
	private boolean end_loop;
	byte[] packetData;
	private int number_of_packets;

	public Recepteur_thread(int port) throws SocketException {
		this.port = port;
		this.end_loop = true;
		this.socketReception = new DatagramSocket(this.port);
		this.number_of_packets=0;
	}

	public boolean get_status_end_loop() {
		return this.end_loop;
	}

	public void change_status_end_loop() {
		this.end_loop = !this.end_loop;
	}
	
	public int get_number_of_packets() {
		return this.number_of_packets;
	}

	public void set_number_of_packets(int nb_packets) {
		this.number_of_packets = nb_packets;
	}
	
	
	public void run() {
		while(true){
			byte[] receiveData = new byte[51024];
			this.packetData = null;
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			try {
				socketReception.receive(receivePacket);
				this.packetData = receivePacket.getData();
			} catch (Exception exc) {
				System.out.println(exc);
			}
			if (this.packetData[0]== (byte)48){
				this.change_status_end_loop();
				break;
			}
			else{
				set_number_of_packets(this.packetData[0]);
			}
		}
	}

	public byte[] get_packetData() {
		return this.packetData;
	}
}
