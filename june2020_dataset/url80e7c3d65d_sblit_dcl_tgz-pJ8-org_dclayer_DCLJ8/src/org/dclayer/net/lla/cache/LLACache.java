package org.dclayer.net.lla.cache;

import java.net.InetAddress;
import java.util.ArrayList;

import org.dclayer.datastructure.tree.ParentTreeNode;
import org.dclayer.listener.net.CachedLLAStatusListener;
import org.dclayer.net.Data;
import org.dclayer.net.lla.CachedLLA;
import org.dclayer.net.lla.InetSocketLLA;
import org.dclayer.net.lla.LLA;

/**
 * Maps lower-level addresses to {@link CachedLLA} objects
 */
public class LLACache {
	
	/**
	 * {@link ArrayList} holding all known addresses
	 */
	private ArrayList<CachedLLA> addresses = new ArrayList<CachedLLA>();
	
	/**
	 * tree used to quickly find IP and port {@link CachedLLA}s
	 */
	private ParentTreeNode<CachedLLA> addressTree = new ParentTreeNode<>(0);
	
	/**
	 * {@link Data} used to write address into for usage as key inside the tree
	 */
	private Data addressBufData = new Data();
	
	private CachedLLAStatusListener cachedLLAStatusListener;
	
	public void setCachedLLAStatusListener(CachedLLAStatusListener cachedLLAStatusListener) {
		this.cachedLLAStatusListener = cachedLLAStatusListener;
	}
	
	public int size() {
		return addresses.size();
	}
	
	// synchronization not needed
	public CachedLLA getCachedLLA(LLA lla, boolean create) {
		
		Data addressData = lla.getData();
		
		CachedLLA cachedLLA = addressTree.get(addressData);
		
		if(cachedLLA == null && create) {
			cachedLLA = new CachedLLA(lla, cachedLLAStatusListener);
			addressTree.put(addressData, cachedLLA);
			synchronized (addresses) {
				addresses.add(cachedLLA);
			}
		}
		
		return cachedLLA;
		
	}
	
	// synchronization not needed as long as this method is only called once at a time (because of addressBufData)
	// (calling getCachedLLA() at the same time as this is no problem though)
	public CachedLLA getIPPortCachedLLA(InetAddress inetAddress, int port, boolean create) {
		
		InetSocketLLA.serialize(inetAddress, port, addressBufData);
		
		CachedLLA cachedLLA = addressTree.get(addressBufData);
		
		if(cachedLLA == null && create) {
			LLA lla = new InetSocketLLA(inetAddress, port);
			cachedLLA = new CachedLLA(lla, cachedLLAStatusListener);
			addressTree.put(addressBufData, cachedLLA);
			synchronized (addresses) {
				addresses.add(cachedLLA);
			}
		}
		
		return cachedLLA;
		
	}
	
}
