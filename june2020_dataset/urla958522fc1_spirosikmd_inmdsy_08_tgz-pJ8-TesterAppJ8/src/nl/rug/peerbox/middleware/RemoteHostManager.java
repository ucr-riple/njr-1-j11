package nl.rug.peerbox.middleware;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.eclipse.swt.internal.image.GIFFileFormat;

public class RemoteHostManager {

	private static final int TIMEOUT = (int)(1000 * 30); // should be smaller then
													// timeout
	private static final int FREQUENCY = 1000 * 1; // should be smaller then
													// timeout
	private static final Logger logger = Logger
			.getLogger(RemoteHostManager.class);

	private final ConcurrentHashMap<Integer, RemoteHost> hosts = new ConcurrentHashMap<Integer, RemoteHost>();
	
	private final List<HostListener> listeners = new ArrayList<HostListener>();

	RemoteHostManager() {

		Timer conntrol = new Timer("RemoteHostManager", true);
		conntrol.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				long current = new Date().getTime();
				for (RemoteHost h : hosts.values()) {
					if (h.getLastLifeSign() + TIMEOUT < current) {
						logger.info(h.getHostID() + "is dead");
						hosts.remove(h.getHostID());
						for (HostListener l : listeners) {
							l.removed(h.getHostID());
						}
					}
				}

			}
		}, 0, FREQUENCY);
	}

	RemoteHost getRemoteHost(int id) {
		return hosts.get(id);
	}

	void addRemoteHost(int peerID, RemoteHost h) {
		logger.debug("Detected group " + h.getHostID() + " with piggyback "
				+ h.getReceivedMessageID());
		hosts.put(peerID, h);
		for (HostListener l : listeners) {
			l.detected(h.getHostID());
		}
	}
	
	public Integer[] getHostIDs() {
		return hosts.keySet().toArray(new Integer[0]);
	}
	
	public void addListener(HostListener l) {
		listeners.add(l);
	}
	
	public void removeListener(HostListener l) {
		listeners.remove(l);
	}

}
