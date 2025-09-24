package nl.rug.peerbox.middleware;


public interface Multicast {

	
	public void announce(byte[] message);
	
	public void addMessageListener(MessageListener ml);
	
	public void shutdown();

	RemoteHostManager getHostManager();	
}
