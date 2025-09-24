package org.dclayer.net.network.routing;

import java.util.Iterator;

import org.dclayer.net.network.component.NetworkPacket;

public class Nexthops implements Iterable<ForwardDestination> {
	
	private Nexthops next;
	private ForwardDestination forwardDestination;
	
	public Nexthops() {
	}
	
	public Nexthops(ForwardDestination forwardDestination) {
		this.forwardDestination = forwardDestination;
	}
	
	public void append(Nexthops nexthops) {
		if(nexthops == this) return; // no loops
		if(next == null) {
			this.next = nexthops;
		} else {
			this.next.append(nexthops);
		}
	}
	
	public void append(ForwardDestination forwardDestination) {
		this.append(new Nexthops(forwardDestination));
	}
	
	public Nexthops getNext() {
		return next;
	}
	
	public void setNext(Nexthops next) {
		this.next = next;
	}
	
	public Nexthops getLast() {
		return next == null ? this : next.getLast();
	}
	
	public ForwardDestination getForwardDestination() {
		return forwardDestination;
	}
	
	public boolean forward(NetworkPacket networkPacket) {
		boolean success = false;
		for(ForwardDestination forwardDestination : this) {
			success |= forwardDestination.onForward(networkPacket);
		}
		return success;
	}

	@Override
	public Iterator<ForwardDestination> iterator() {
		return new Iterator<ForwardDestination>() {
			
			private Nexthops current = Nexthops.this;
			
			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public ForwardDestination next() {
				ForwardDestination forwardDestination = current.forwardDestination;
				current = current.next;
				return forwardDestination;
			}

			@Override
			public void remove() {
				
			}
			
		};
	}
	
}
