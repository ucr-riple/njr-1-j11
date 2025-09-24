package org.dclayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.dclayer.listener.net.CachedLLAStatusListener;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.lla.CachedLLA;
import org.dclayer.net.lla.LLA;
import org.dclayer.net.lla.database.LLADatabase;
import org.dclayer.net.lla.database.LLADatabase.LLADatabaseCursor;

public class ConnectionManager extends Thread implements CachedLLAStatusListener, HierarchicalLevel {
	
	public static class ConnectAttempt {
		CachedLLA cachedLLA;
		int numAttempts;
		long nextAttempt;
		
		public ConnectAttempt(CachedLLA cachedLLA) {
			this.cachedLLA = cachedLLA;
		}
	}
	
	/**
	 * the initial delay between sending messages to initiate a connection with an LLA.
	 * the delay will be increased during connection initiation
	 */
	public static final int CONNECT_STARTDELAY_MILLIS = 500;
	
	/**
	 * the number of messages after which to give up trying to initiate a connection with an LLA
	 */
	public static final int MAX_NUM_CONNECTION_INIT_MSGS = 5;
	
	//

	private DCLService dclService;
	
	private LLADatabase llaDatabase;
	private LLADatabaseCursor llaDatabaseCursor;
	
	private LinkedList<ConnectAttempt> connectAttempts = new LinkedList<>();
	
	private LinkedList<CachedLLA> connectedCachedLLAs = new LinkedList<>();
	
	private Data punchData = new Data(16);
	
	public ConnectionManager(DCLService dclService, LLADatabase llaDatabase) {
		this.dclService = dclService;
		this.llaDatabase = llaDatabase;
		this.llaDatabaseCursor = llaDatabase.makeCursor();
		this.start();
	}

	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return dclService;
	}
	
	@Override
	public String toString() {
		return "ConnectionManager";
	}
	
	@Override
	public synchronized void run() {
		
		ConnectAttempt connectAttempt;
		
		for(;;) {
				
			try {
				connectAttempt = connectAttempts.getFirst();	
			} catch(NoSuchElementException e) {
				connectAttempt = null;
			}
			
			long timeout = 0;
			
			if(connectAttempt != null) {
				
				long now = System.nanoTime() / 1000000L;
				
				// allow inaccuracy of up to 10ms
				if(now >= (connectAttempt.nextAttempt - 10)) {
					
					connectAttempts.removeFirst();
					
					if(connectAttempt.cachedLLA.getStatus() != CachedLLA.CONNECTING_PRELINK) {
						continue;
					} else if(connectAttempt.numAttempts >= MAX_NUM_CONNECTION_INIT_MSGS) {
						connectFailed(connectAttempt.cachedLLA);
						continue;
					}
					
					punch(connectAttempt.cachedLLA);
					
					connectAttempt.nextAttempt = now + (CONNECT_STARTDELAY_MILLIS << connectAttempt.numAttempts);
					connectAttempt.numAttempts++;
					
					connectAttempts.addLast(connectAttempt);
					
					continue;
					
				}
				
				timeout = connectAttempt.nextAttempt - now;
				
			}
			
			if(timeout == 0 && checkNeedConnect()) {
				continue;
			}
			
			try {
				this.wait(timeout <= 0 ? 5000 : timeout);
			} catch (InterruptedException e1) {
				return;
			}
			
		}
		
	}
	
	private void connectFailed(CachedLLA cachedLLA) {
		cachedLLA.changeStatus(CachedLLA.CONNECTING_PRELINK, CachedLLA.DISCONNECTED);
		Log.debug(ConnectionManager.this, "could not connect to %s", cachedLLA);
	}
	
	private boolean checkNeedConnect() {
		// TODO
		Log.debug(this, "checkNeedConnect: %d connectedCachedLLAs, %d connectAttempts", connectedCachedLLAs.size(), connectAttempts.size());
		if(connectedCachedLLAs.size() < 100 && connectAttempts.size() < 10) {
			List<LLA> llas = llaDatabase.getLLAs(llaDatabaseCursor, 10);
			for(LLA lla : llas) {
				dclService.connect(lla);
			}
			return llas.size() > 0;
		}
		return false;
	}
	
	public synchronized void connect(final CachedLLA cachedLLA) {
		
		if(!cachedLLA.changeStatus(CachedLLA.DISCONNECTED, CachedLLA.CONNECTING_PRELINK)) {
			Log.debug(this, "not connecting to %s (status not disconnected)", cachedLLA);
			return;
		}
		
		Log.debug(this, "attempting to connect to %s", cachedLLA);
		
		ConnectAttempt connectAttempt = new ConnectAttempt(cachedLLA);
		connectAttempts.add(connectAttempt);
		
		this.notify();
		
	}
	
	public void punch(CachedLLA cachedLLA, Data punchData) {
		cachedLLA.setPunchData(punchData.copy());
		dclService.send(cachedLLA, punchData);
	}
	
	public synchronized void punch(CachedLLA cachedLLA) {
		new Random().nextBytes(punchData.getData());
		punchData.reset(0, (int)((Math.random() + 1)/2 * punchData.getData().length));
		punch(cachedLLA, punchData);
	}

	@Override
	public void onStatusChanged(CachedLLA cachedLLA, int oldStatus, int newStatus) {
		synchronized(connectedCachedLLAs) {
			
			Log.debug(this, "cached LLA %s changed status: %s -> %s", cachedLLA, CachedLLA.STATUS_NAMES[oldStatus], CachedLLA.STATUS_NAMES[newStatus]);
			
			if(oldStatus == CachedLLA.CONNECTED) {
				connectedCachedLLAs.remove(cachedLLA);
			}
			if(newStatus == CachedLLA.CONNECTED) {
				connectedCachedLLAs.add(cachedLLA);
			}
			
		}
	}
	
	/**
	 * returns a random selection of at max limit connected lower-level addresses.
	 * duplicates might be returned.
	 * @param limit how much lower-level addresses to return at max
	 * @return a random selection of connected lower-level addresses. duplicates might be returned.
	 */
	public List<LLA> getRandomConnectedLLAs(int limit) {
		
		synchronized(connectedCachedLLAs) {
			
			final int size = connectedCachedLLAs.size();
			if(limit > size) limit = size;
			
			ArrayList<LLA> list = new ArrayList<>(limit);
			
			for(int i = 0; i < limit; i++) {
				list.add(connectedCachedLLAs.get((int)(Math.random() * size)).getLLA());
			}
			
			return list;
			
		}
		
	}
	
	/**
	 * @deprecated Only for use with JUnit test cases
	 */
	@Deprecated
	public LinkedList<CachedLLA> getConnectedCachedLLAs() {
		return connectedCachedLLAs;
	}
	
}
