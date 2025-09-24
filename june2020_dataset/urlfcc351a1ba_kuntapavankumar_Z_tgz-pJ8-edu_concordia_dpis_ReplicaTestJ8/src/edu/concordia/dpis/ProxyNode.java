package edu.concordia.dpis;

import edu.concordia.dpis.commons.Address;
import edu.concordia.dpis.commons.DeadNodeException;
import edu.concordia.dpis.commons.Message;
import edu.concordia.dpis.commons.ReliableMessage;
import edu.concordia.dpis.messenger.UDPClient;

// A proxy working on behalf of a remote node, simply delegates the request over UDP to the actual node
public class ProxyNode implements Node {

	private Address address;

	private UDPClient udpClient;

	public ProxyNode(Address address) {
		this.address = address;
		this.udpClient = new UDPClient();
	}

	@Override
	public String getLeaderName() throws DeadNodeException {
		Message fromMessage = sendMessage("getLeaderName");
		if (fromMessage == null) {
			return "";
		}
		return fromMessage.getActualMessage();
	}

	private Message sendMessage(String operationName, int timeout,
			String... params) throws DeadNodeException {
		Message toMessage = newMessage(operationName, params);
		Message fromMessage = null;
		try {
			fromMessage = this.udpClient.send(toMessage, timeout);
		} catch (edu.concordia.dpis.commons.TimeoutException e) {
			throw new DeadNodeException();
		}
		return fromMessage;
	}

	private Message sendMessage(String operationName, String... params)
			throws DeadNodeException {
		return sendMessage(operationName, 0, params);
	}

	private Message newMessage(String operationName, String... params)
			throws DeadNodeException {
		ReliableMessage rMsg = new ReliableMessage(operationName,
				this.address.getHost(), this.address.getPort());

		for (String param : params) {
			rMsg.addArgument(param);
		}
		return rMsg;
	}

	@Override
	public void newLeader(String name) throws DeadNodeException {
		sendMessage("newLeader", 2000, name);
	}

	@Override
	public MessageType election(String name) throws DeadNodeException {
		Message msg = sendMessage("election", 2000, name);
		return MessageType.valueOf(msg.getActualMessage());
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public boolean isAlive() {
		try {
			Message msg = sendMessage("isAlive", 2000);
			if (msg != null && msg.getActualMessage() != null) {
				return true;
			}
		} catch (DeadNodeException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
}
