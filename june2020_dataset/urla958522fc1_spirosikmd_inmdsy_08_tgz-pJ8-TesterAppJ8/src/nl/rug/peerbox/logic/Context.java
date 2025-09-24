package nl.rug.peerbox.logic;

import java.util.Collection;

import nl.rug.peerbox.logic.filesystem.VirtualFileSystem;
import nl.rug.peerbox.middleware.Multicast;

public interface Context {

	Multicast getMulticastGroup();

	String getPathToPeerbox();

	Peer getLocalPeer();

	VirtualFileSystem getVirtualFilesystem();

	String getDatafileName();

	void join();

	void leave();

	void addPeerListener(PeerListener l);
	
	void removePeerListener(PeerListener l);

	void peerLeft(Peer peer);

	Collection<PeerHost> getPeers();

}
