package org.dclayer.net.network;

import java.util.Iterator;
import java.util.LinkedList;

public class NetworkInstanceCollection implements Iterable<NetworkInstance> {
	
	private LinkedList<NetworkInstance> networkInstances = new LinkedList<>();
	private NetworkInstance[] curArray;
	
	public synchronized void addNetworkInstance(NetworkInstance networkInstance) {
		
		curArray = null;
		
		for(NetworkInstance existingNetworkInstance : networkInstances) {
			if(existingNetworkInstance.getNetworkType().equals(networkInstance.getNetworkType())) {
				networkInstance.getRoutingTable().connect(existingNetworkInstance.getRoutingTable());
				break;
			}
		}
		
		this.networkInstances.add(networkInstance);
		
	}
	
	public synchronized NetworkInstance findFirst(NetworkType findNetworkType) {
		for(NetworkInstance networkInstance : networkInstances) {
			if(networkInstance.getNetworkType().equals(findNetworkType)) return networkInstance;
		}
		return null;
	}
	
	public Iterable<NetworkInstance> findAll(final NetworkType findNetworkType) {
		return new Iterable<NetworkInstance>() {
			@Override
			public Iterator<NetworkInstance> iterator() {
				
				return new Iterator<NetworkInstance>() {
					
					private Iterator<NetworkInstance> iterator = NetworkInstanceCollection.this.iterator();
					private NetworkInstance next;
					
					{
						seek();
					}
					
					private void seek() {
						while((iterator.hasNext() || (next = null) != null) && !(next = iterator.next()).getNetworkType().equals(findNetworkType));
					}
					
					@Override
					public boolean hasNext() {
						return next != null;
					}
					
					@Override
					public NetworkInstance next() {
						NetworkInstance cur = next;
						seek();
						return cur;
					}
					
					@Override
					public void remove() {}
					
				};
				
			}
		};
	}
	
	public NetworkNode[] copyArray() {
		if(curArray == null) {
			synchronized(this) {
				curArray = this.networkInstances.toArray(new NetworkInstance[this.networkInstances.size()]);
			}
		}
		return curArray;
	}

	@Override
	public Iterator<NetworkInstance> iterator() {
		return networkInstances.iterator();
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		boolean comma = false;
		for(NetworkInstance networkInstance : networkInstances) {
			if(comma) stringBuilder.append(", ");
			else comma = true;
			stringBuilder.append(networkInstance.toString());
		}
		return stringBuilder.toString();
	}
	
}
