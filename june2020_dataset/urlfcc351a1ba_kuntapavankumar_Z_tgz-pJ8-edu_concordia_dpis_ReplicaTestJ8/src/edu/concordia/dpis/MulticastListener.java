package edu.concordia.dpis;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import edu.concordia.dpis.commons.Message;
import edu.concordia.dpis.commons.MessageTransformer;
import edu.concordia.dpis.commons.ReliableMessage;
import edu.concordia.dpis.commons.TimeoutException;
import edu.concordia.dpis.messenger.UDPClient;

// A group subscriber
public class MulticastListener {

	// listen port
	private int port;
	// listen group ip
	private String group;
	// socket
	private MulticastSocket multicastSocket;
	// is unsubscribed
	private boolean isClosed = true;

	public MulticastListener(int port, String group) {
		this.port = port;
		this.group = group;
	}

	// subscribe to the group
	public void joinGroup() {
		try {
			this.multicastSocket = new MulticastSocket(port);
			InetAddress group = InetAddress.getByName(this.group);
			this.multicastSocket.joinGroup(group);
			this.isClosed = false;
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("multicast listener getting ready...");
					// listen for the messages continuosly
					while (true) {
						if (!multicastSocket.isClosed()) {
							byte buf[] = new byte[1024];
							DatagramPacket pack = new DatagramPacket(buf,
									buf.length);
							try {
								// listen
								multicastSocket.receive(pack);
								System.out
										.println("received a packet from multisocket in multicastlistener");
								// unmarshal the request message
								ReliableMessage request = (ReliableMessage) MessageTransformer
										.deserializeMessage(pack.getData());
								// handle the request message
								Message replyMsg = onMessage(request);
								// if this message needs to be replied back
								if (replyMsg.isReply()
										&& replyMsg.isReplyToThisMessage()) {
									UDPClient.INSTANCE.send(replyMsg, 3000);
								}
							} catch (IOException e) {
								e.printStackTrace();
							} catch (TimeoutException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Message onMessage(ReliableMessage msg) {
		System.out.println("Got a message from multicast port");
		return null;
	}

	// unsubscribe from the group
	public void leaveGroup() {
		try {
			this.multicastSocket.leaveGroup(InetAddress.getByName(group));
			this.multicastSocket.close();
			this.isClosed = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			//
		}
	}

	public boolean isClosed() {
		return isClosed;
	}
}
