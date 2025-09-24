package nl.rug.peerbox.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nl.rug.peerbox.middleware.HostListener;
import nl.rug.peerbox.middleware.Multicast;

public class PeerManager implements HostListener {

	private final ConcurrentHashMap<Integer, Peer> peers = new ConcurrentHashMap<Integer, Peer>();
	private final List<PeerListener> listener = new ArrayList<PeerListener>();

	public PeerManager(Multicast multicast) {
		multicast.getHostManager().addListener(this);
	}

	public void updatePeer(int hostID, Peer peer) {
		boolean firstEncounter = (peers.putIfAbsent(hostID, peer) == null);
		
		synchronized (listener) {
			for (PeerListener l : listener) {
				if (firstEncounter) {
					l.joined(new PeerHost(hostID, peer));
				} else {
					l.updated(new PeerHost(hostID, peer));	
				}
			}
		}
	}

	public void removePeer(Peer peer) {
		int hostID = -1;
		for (Map.Entry<Integer, Peer> entry : peers.entrySet()) {
			if (entry.getValue().equals(peer)) {
				hostID = entry.getKey();
			}
		}
		if (peers.remove(hostID) != null) {
			synchronized (listener) {
				for (PeerListener l : listener) {
					l.deleted(new PeerHost(hostID, peer));
				}
			}
		}
	}

	@Override
	public void removed(int hostID) {
		Peer p = peers.remove(hostID);
		synchronized (listener) {
			for (PeerListener l : listener) {
				l.deleted(new PeerHost(hostID, p));
			}
		}
	}

	@Override
	public void detected(int hostID) {
		synchronized (listener) {
			for (PeerListener l : listener) {
				l.joined(new PeerHost(hostID, peers.get(hostID)));
			}
		}
	}
	

	public void addPeerListener(PeerListener l) {
		synchronized (listener) {
			listener.add(l);
		}
	}

	public void removePeerListener(PeerListener l) {
		synchronized (listener) {
			listener.remove(l);
		}
	}
	
	public Collection<PeerHost> getPeers() {
		Context ctx = Peerbox.getInstance();
		Collection<PeerHost> peerHosts = new  LinkedList<>();
		for (int hostID : ctx.getMulticastGroup().getHostManager().getHostIDs()) {
			peerHosts.add(new PeerHost(hostID, peers.get(hostID)));
		}
		return peerHosts;
	}

}
