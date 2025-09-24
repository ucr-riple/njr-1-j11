package nl.rug.peerbox.logic;

public final class PeerHost {
	
	private final int hostID;
	private final  Peer peer;
	
	PeerHost(int hostID, Peer peer) {
		this.hostID = hostID;
		this.peer = peer;
	}
	
	public final int getHostID() {
		return hostID;
	}
	public final Peer getPeer() {
		return peer;
	}	
}
