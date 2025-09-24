
package edu.concordia.dpis.fifo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender {

	private int port;
	private String group;
	private MulticastSocket multicastSocket;

	public MulticastSender(int port, String group) {
		this.port = port;
		this.group = group;
		try {
			this.multicastSocket = new MulticastSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(byte[] payload) throws IOException {
		final DatagramPacket pack = new DatagramPacket(payload, payload.length,
				InetAddress.getByName(group), port);
		this.multicastSocket.send(pack);
	}

	public void close() {
		if (this.multicastSocket != null) {
			if (!this.multicastSocket.isClosed()) {
				this.multicastSocket.close();
			}
		}
	}
}
