import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;

import RQLibrary.EncodingSymbol;

public class Treatment extends Thread {
    final static int size_int = 4;
    byte[][] matrice;
    long[] temps = new long[1000];
    private final Set<EncodingSymbol> receivedPackets = new HashSet<EncodingSymbol>();
    int RTT;
    boolean start;
    boolean boucle;
    int nb;
    int nbRecv;
    int ESI;
    
    Treatment(int rtt) {
	
	RTT = rtt;
	
	for (int i = 0; i < temps.length; i++) {
	    temps[i] = 0;
	}
	nb = 0;
	nbRecv = 0;
	start = true;
	boucle = true;
	ESI = 1;
	start();
	
    }
    
    int getESI() {
	return this.ESI;
    }
    
    int getNb() {
	return this.nb;
    }
    
    int getNbRecv() {
	return this.nbRecv;
    }
    
    void setEndBoucle() {
	boucle = false;
    }
    
    public void run() {
	// look and compare all time if > RTT pop it
	while (boucle) {
	    for (int i = 0; i < temps.length; i++) {
		
		if ( temps[i] != 0 ) {
		    // if ( System.currentTimeMillis() - temps[i] > 500 ) {
		    if ( System.currentTimeMillis() - temps[i] > this.RTT ) {
			nbRecv++;
			temps[i] = 0;
			receivedPackets.add((EncodingSymbol) treatPacket(matrice[i]));
		    }
		}
	    }
	    try {
		Thread.sleep(10);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }
    
    EncodingSymbol treatPacket(byte[] packet) {
	int T = Utils.SYMB_LENGTH;
	byte[] sbn = new byte[4];
	byte[] esi = new byte[4];
	byte[] data = new byte[T];
	
	System.arraycopy(packet, 0, sbn, 0, size_int);
	System.arraycopy(packet, size_int, esi, 0, size_int);
	System.arraycopy(packet, size_int * 2, data, 0, T);
	// System.out.println("      SBN      "+byte_array_to_int(sbn)+"            ESI                "+byte_array_to_int(esi));
	this.ESI = Utils.byteArrayToInt(esi);
	EncodingSymbol symbols = new EncodingSymbol(Utils.byteArrayToInt(sbn), Utils.byteArrayToInt(esi), data);
	
	return symbols;
    }
    
    void addPacket(byte[] packet) {
	// Add a packet and write the entering time
	nb++;
	if ( start ) {
	    matrice = new byte[1000][packet.length];
	    start = !start;
	}
	
	for (int i = 0; i < temps.length; i++) {
	    if ( temps[i] == 0 ) {
		temps[i] = System.currentTimeMillis();
		System.arraycopy(packet, 0, matrice[i], 0, packet.length);
		break;
	    }
	    
	}
	
    }
    
    Set<EncodingSymbol> getEncodingSymbol() {
	Set<EncodingSymbol> tmp = new HashSet<EncodingSymbol>();
	synchronized (receivedPackets) {
	    tmp.addAll(receivedPackets);
	}
	return tmp;
	
    }
    
}
