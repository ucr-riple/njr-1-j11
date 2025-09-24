import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class RecepteurThreadMulti extends Thread {
    
    private int port;
    private MulticastSocket socketReception;
    private boolean endLoop;
    private boolean historic;
    int packetData;
    private int numberOfPackets;
    private int endReception;
    private int nbOfReceiver;
    private int totalSymb;
    private long time_1;
    private long time_2;
    private int nbAck;
    private long before;
    private boolean needMore;
    // private double redondancy;
    private float[] tab_lost = new float[50];
    
    public RecepteurThreadMulti(int port, InetAddress groupeIP, int nbOfRecv, boolean historic) throws IOException {
	this.port = port;
	this.endLoop = true;
	this.needMore = false;
	this.socketReception = new MulticastSocket(port);
	this.socketReception.joinGroup(groupeIP);
	this.numberOfPackets = 0;
	this.endReception = 1;
	this.packetData = 0;
	this.nbOfReceiver = nbOfRecv;
	this.totalSymb = 1;
	this.time_1 = 0;
	this.time_2 = 0;
	this.nbAck = 0;
	this.historic = historic;
	for (int i = 0; i < tab_lost.length; i++)
	    this.tab_lost[i] = 0;
	
    }
    
    public boolean getNeedMore() {
	return this.needMore;
    }
    
    public void setNeedMore(boolean var) {
	this.needMore = var;
    }
    
    public boolean getStatusEndLoop() {
	return this.endLoop;
    }
    
    public void changeStatusEndLoop() {
	this.endLoop = false;
    }
    
    public void setTotalNumberOfPackets(int nb_packet) {
	this.totalSymb = nb_packet;
    }
    
    public int getNumberOfPackets() {
	return this.numberOfPackets;
    }
    
    public void setNumberOfPackets(int nb_packets) {
	this.needMore = true;
	if ( this.numberOfPackets <= nb_packets ) {
	    
	    if ( this.historic ) {
		
		for (int i = 0; i < tab_lost.length; i++) {
		    if ( tab_lost[i] == 0.0 ) {
			tab_lost[i] = ((float) nb_packets / (float) this.totalSymb);
			break;
		    }
		}
		
		System.out.println("             " + nb_packets + "       " + this.totalSymb);
		System.out.println("      Plost                " + ((float) nb_packets / (float) this.totalSymb) + "            " + tab_lost[0]
			+ "            " + tab_lost[1] + "            " + tab_lost[2]);
		int packet_sup = 10;
		this.numberOfPackets = (int) Math.round((((float) nb_packets / (float) this.totalSymb) + 1) * (float) nb_packets) + packet_sup;
		
	    } else
		this.numberOfPackets = nb_packets;
	    
	    setTotalNumberOfPackets(this.numberOfPackets);
	    
	}
	
    }
    
    public void resetNumberOfPackets() {
	this.numberOfPackets = 0;
    }
    
    public long getTime_1() {
	return this.time_1;
    }
    
    public long getTime_2() {
	return this.time_2;
    }
    
    public int getNbAck() {
	return this.nbAck;
    }
    
    public void setTimeSimu() {
	this.before = System.currentTimeMillis();
    }
    
    public void run() {
	
	while (true) {
	    byte[] receiveData = new byte[50];
	    
	    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    
	    try {
		socketReception.receive(receivePacket);
		byte[] packetData_2 = receivePacket.getData();
		for (int l = 0; l < Utils.IntegerSize; l++) {
		    this.packetData = (this.packetData << 8) + (packetData_2[l] & 0xff);
		}
		this.needMore = false;
	    } catch (Exception exc) {
		System.out.println(exc);
	    }
	    System.out.println("On a recu :                " + this.packetData);
	    this.nbAck++;
	    
	    try {
		Thread.sleep(Utils.RTT);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    if ( this.packetData == Utils.FLAG_PUSH ) {
		setNumberOfPackets(this.packetData);
		break;
	    }
	    
	    if ( this.packetData == Utils.NACK ) {
		this.needMore = true;
		setNumberOfPackets(1);
		continue;
	    }
	    
	    if ( this.packetData == Utils.ACK ) {
		
		if ( time_1 == 0 ) {
		    time_1 = System.currentTimeMillis() - before;
		}
		endReception++;
		if ( endReception > this.nbOfReceiver ) {
		    this.numberOfPackets = 0;
		    this.changeStatusEndLoop();
		    time_2 = System.currentTimeMillis() - before;
		    Utils.printInFile(String.valueOf(System.currentTimeMillis()), port, 1);
		    break;
		}
	    } else {
		setNumberOfPackets(this.packetData);
	    }
	}
    }
}
