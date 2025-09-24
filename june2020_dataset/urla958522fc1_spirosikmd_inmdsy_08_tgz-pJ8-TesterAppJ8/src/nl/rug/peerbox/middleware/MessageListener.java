package nl.rug.peerbox.middleware;

public interface MessageListener {
	
	public void receivedMessage(byte[] payload, int hostID);
	
}
