package nl.rug.peerbox.logic;

public interface PeerListener {
	
	void updated(PeerHost ph);
	void deleted(PeerHost ph);
	void joined(PeerHost peerHost);

}
