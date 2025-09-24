package edu.concordia.dpis.messenger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import edu.concordia.dpis.commons.Message;
import edu.concordia.dpis.commons.MessageTransformer;

// UDP SERVER
public abstract class UDPServer {
	private DatagramSocket aSocket = null;
	private final int port;

	public UDPServer(int port) {
		this.port = port;
	}

	public void start() {
		new Thread(new Runnable() {
			public void run() {
				try {
					aSocket = new DatagramSocket(port);
					System.out.println("UDPServer is up and running on port"
							+ port);
					while (true) {
						try {
							byte[] buffer = new byte[1000];
							final DatagramPacket request = new DatagramPacket(
									buffer, buffer.length);
							aSocket.receive(request);

							new Thread(new Runnable() {

								@Override
								public void run() {
									try {
										Message msg = MessageTransformer
												.deserializeMessage(request
														.getData());
										String output = getReplyMessage(msg);
										if (msg.isReplyToThisMessage()) {
											byte[] payload = output
													.getBytes("US-ASCII");
											DatagramPacket reply = new DatagramPacket(
													payload, payload.length,
													request.getAddress(),
													request.getPort());
											aSocket.send(reply);
										}

									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}

							}).start();

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

				} catch (SocketException e) {
					System.out.println("Socket: " + e.getMessage());
				} finally {
					if (aSocket != null) {
						aSocket.close();
					}
				}
			}
		}).start();
	}

	public void close() {
		if (aSocket != null) {
			aSocket.close();
		}
	}

	protected abstract String getReplyMessage(Message message);
}
