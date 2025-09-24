package org.dclayer.net.network.slot;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dclayer.datastructure.map.slotmap.Slot;
import org.dclayer.net.address.Address;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.NetworkType;
import org.dclayer.net.network.component.NetworkPacket;


public class GenericNetworkSlot<T extends NetworkNode> extends Slot<NetworkType> {
	
	private NetworkType networkType;
	private NetworkPacket networkPacket;
	
	private List<T> networkNodes = new LinkedList<>();
	
	private GenericNetworkSlot remoteEquivalent;
	
	public GenericNetworkSlot(int slotId, NetworkType networkType) {
		super(slotId);
		this.networkType = networkType;
		this.networkPacket = networkType.makeNetworkPacket(this);
	}
	
	public NetworkPacket getNetworkPacket() {
		return networkPacket;
	}
	
	public void setRemoteEquivalent(GenericNetworkSlot remoteEquivalent) {
		this.remoteEquivalent = remoteEquivalent;
	}
	
	public GenericNetworkSlot getRemoteEquivalent() {
		return remoteEquivalent;
	}
	
	public NetworkType getNetworkType() {
		return networkType;
	}

	@Override
	public NetworkType getSearchObject() {
		return networkType;
	}
	
	public void addNetworkNode(T networkNode) {
		networkNodes.add(networkNode);
	}
	
	public T removeNetworkNode(Address address) {
		
		Iterator<T> iterator = networkNodes.iterator();
		T networkNode;
		
		while(iterator.hasNext()) {
			networkNode = iterator.next();
			if(networkNode.getAddress() == address) {
				iterator.remove();
				return networkNode;
			}
		}
		
		return null;
	}
	
	public List<T> getNetworkNodes() {
		return networkNodes;
	}
	
	@Override
	public String contentToString() {
		return String.format("%s, %d nodes, %s remote equivalent", networkType, networkNodes.size(), remoteEquivalent == null ? "no" : "has");
	}
	
}
