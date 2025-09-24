package org.dclayer.net.circle;

import java.util.LinkedList;

import org.dclayer.datastructure.tree.ParentTreeNode;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.address.Address;
import org.dclayer.net.network.NetworkInstance;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.NetworkType;
import org.dclayer.net.network.component.NetworkPacket;
import org.dclayer.net.network.routing.ForwardDestination;
import org.dclayer.net.network.routing.Nexthops;
import org.dclayer.net.network.routing.RoutingTable;

public class CircleRoutingTable extends RoutingTable implements HierarchicalLevel {
	
	private CircleNetworkType circleNetworkType;
	
	private NetworkInstance localNetworkInstance;
	
	private ParentTreeNode<Nexthops> remoteServiceRoutes = new ParentTreeNode<>(0);
	private ParentTreeNode<Nexthops> localEndpointRoutes = new ParentTreeNode<>(0);
	
	private LinkedList<NetworkNode> networkNodes = new LinkedList<>();
	
	private LinkedList<NetworkNode> networkNodesCopiedToConnectedTable = null;
	private CircleRoutingTable connectedCircleRoutingTable = null;
	
	public <T extends NetworkPacket> CircleRoutingTable(CircleNetworkType circleNetworkType, NetworkInstance networkInstance) {
		this.circleNetworkType = circleNetworkType;
		this.localNetworkInstance = networkInstance;
		this.add(networkInstance);
	}

	@Override
	public synchronized boolean add(NetworkNode networkNode) {
		
		if(connectedCircleRoutingTable != null) {
			return connectedCircleRoutingTable.add(networkNode);
		}
		
		ParentTreeNode<Nexthops> routes = networkNode.isEndpoint() ? localEndpointRoutes : remoteServiceRoutes;
		
		Data scaledDestinationAddress = networkNode.getScaledAddress();
		
		Nexthops nexthops = routes.get(scaledDestinationAddress);
		if(nexthops == null) {
			nexthops = new Nexthops(networkNode);
			routes.put(scaledDestinationAddress, nexthops);
		} else {
			for(ForwardDestination forwardDestination : nexthops) {
				if(networkNode.getAddress().equals(forwardDestination.getAddress())) {
					return false;
				}
			}
			nexthops.append(networkNode);
		}
		
		networkNodes.add(networkNode);
		
		return true;
		
	}

	@Override
	public synchronized boolean remove(NetworkNode networkNode) {
		
		if(connectedCircleRoutingTable != null) {
			return connectedCircleRoutingTable.remove(networkNode);
		}
		
		ParentTreeNode<Nexthops> routes = networkNode.isEndpoint() ? localEndpointRoutes : remoteServiceRoutes;
		
		Data scaledDestinationAddress = networkNode.getScaledAddress();
		
		Nexthops nexthops = routes.get(scaledDestinationAddress);
		if(nexthops == null) {
			return false;
		}

		boolean success = false;
		Nexthops lastNexthops = null;
		
		do {
			
			ForwardDestination forwardDestination = nexthops.getForwardDestination();
			if(networkNode.getAddress().equals(forwardDestination.getAddress())) {
				
				if(lastNexthops == null) {
					// the first Nexthops element is to be removed
					
					if(nexthops.getNext() != null) {
						// there are others with the same scaled address, update the reference in the tree
						routes.put(scaledDestinationAddress, nexthops.getNext());
						
					} else {
						// this is the only one, remove the reference from the tree
						routes.remove(scaledDestinationAddress);
						
					}
					
				} else {
					
					// the Nexthops element to be removed is not the first, no need to update the reference in the tree
					lastNexthops.setNext(nexthops.getNext());
					
				}
				
				success = true;
				
			} else {
				
				// update lastNexthops only if we didn't remove anything
				// (since we'd be setting lastNexthops to the Nexthops object we just removed otherwise)
				lastNexthops = nexthops;
				
			}
			
			nexthops = nexthops.getNext();
			
		} while(nexthops != null);
		
		networkNodes.remove(networkNode);
		
		return success;
		
	}

	@Override
	public synchronized Nexthops lookup(Data scaledDestinationAddress, Address originAddress, Object originIdentifierObject, int offset) {
		
		if(connectedCircleRoutingTable != null) {
			return connectedCircleRoutingTable.lookup(scaledDestinationAddress, originAddress, originIdentifierObject, offset);
		}
		
		// check if the destination address refers to one of our local endpoints
		Nexthops nexthops = localEndpointRoutes.get(scaledDestinationAddress);
		if(nexthops != null) {
			// great, the packet belongs to us
			
			// before returning the list of nexthops (which are only local endpoints at this point), check
			// if there are any neighbors with the same scaled address and if yes, add them to the nexthops
			// list, as long as we did not already receive this packet from such a twin neighbor
			Nexthops twinNeighbors = remoteServiceRoutes.get(scaledDestinationAddress); // here, use get() instead of getClosest()
			if(twinNeighbors != null) {
				
				boolean append = true;
				
				for(ForwardDestination forwardDestination : twinNeighbors) {
					if(forwardDestination.getIdentifierObject() == originIdentifierObject) {
						// if we already received the packet from such a twin neighbor,
						// don't loop-route it back.
						// note: all the hops in the twinNexthops list share the same scaled address
						//       as us, and if one of their ForwardDestinations holds the originAddress
						//       Address instance, we received the packet from that hop, who also takes
						//       care of forwarding the packet to the other twins. just locally deliver the packet.
						append = false;
						break;
					}
				}
				
				if(append) {
					nexthops.append(twinNeighbors);
				}
				
			}
			
			return nexthops;
		}
		
		// looks like we don't have a local endpoint for this address, so check if we can forward
		// it to somebody who's closer to the destination
		
		nexthops = remoteServiceRoutes.getClosest(scaledDestinationAddress);
		
		if(nexthops == null) {
			// nothing found
			return null;
		}
		
		for(ForwardDestination forwardDestination : nexthops) {
			if(forwardDestination.getIdentifierObject() == originIdentifierObject) {
				// to prevent loop-routing, do not forward the packet at all if the
				// the hop we just received it from is included in the nexthops list
				return null;
			}
		}
		
		// now, only forward to one hop. if there are more than one hops with the same
		// scaled address (which is the case if nexthops contains more than one
		// ForwardDestination), the hop we forward the packet to will be connected to its
		// twins and share the packet with them.
		
		return nexthops.getLast();
		
	}

	@Override
	public NetworkType getNetworkType() {
		return circleNetworkType;
	}

	@Override
	public synchronized void connect(RoutingTable routingTable) {
		
		Log.debug(this, "connecting with %s", routingTable);
		
		if(networkNodesCopiedToConnectedTable != null) {
			Log.debug(this, "disconnecting first");
			disconnect();
		}
		
		connectedCircleRoutingTable = (CircleRoutingTable) routingTable;
		
		networkNodesCopiedToConnectedTable = new LinkedList<>();
		
		for(NetworkNode networkNode : networkNodes) {
			if(connectedCircleRoutingTable.add(networkNode)) {
				Log.debug(this, "copied NetworkNode: %s", networkNode);
				networkNodesCopiedToConnectedTable.add(networkNode);
			} else {
				Log.debug(this, "NetworkNode not copied: %s", networkNode);
			}
		}
		
		// clear local table, refill it when disconnecting
		this.remoteServiceRoutes = null;
		this.localEndpointRoutes = null;
		this.networkNodes.clear();
		
	}

	@Override
	public synchronized void disconnect() {
		
		this.remoteServiceRoutes = new ParentTreeNode<>(0);
		this.localEndpointRoutes = new ParentTreeNode<>(0);
		
		for(NetworkNode networkNode : networkNodesCopiedToConnectedTable) {
			
			if(connectedCircleRoutingTable.remove(networkNode)) {
				Log.debug(this, "removed NetworkNode from connected table: %s", networkNode);
			} else {
				Log.debug(this, "NetworNode not removed from connected table: %s", networkNode);
			}
			
			if(add(networkNode)) {
				Log.debug(this, "reinserted NetworkNode: %s", networkNode);
			} else {
				Log.debug(this, "NetworkNode not reinserted: %s", networkNode);
			}
			
		}
		
		networkNodesCopiedToConnectedTable = null;
		connectedCircleRoutingTable = null;
		
	}

	@Override
	public String toString() {
		return "CircleRoutingTable";
	}

	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return localNetworkInstance;
	}
	
}
