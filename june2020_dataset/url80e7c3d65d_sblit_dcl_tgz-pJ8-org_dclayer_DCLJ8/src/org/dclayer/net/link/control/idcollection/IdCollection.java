package org.dclayer.net.link.control.idcollection;
import java.util.Iterator;

/**
 * a collection of blocks of IDs
 */
public class IdCollection implements Iterable<IdBoundary> {
	
	/**
	 * the first {@link IdBoundary}.
	 */
	private IdBoundary idBoundary;
	
	/**
	 * the highest id included
	 */
	private long highestId;
	/**
	 * the lowest id included
	 */
	private long lowestId;
	/**
	 * the amount of different ids included
	 */
	private long numIds;
	
	public IdCollection() {
		reset();
	}
	
	/**
	 * resets this {@link IdCollection}, removing all its contained IDs
	 */
	public synchronized void reset() {
		this.idBoundary = null;
		this.highestId = -1;
		this.lowestId = -1;
		this.numIds = 0;
	}
	
	/**
	 * adds an id to this {@link IdCollection}
	 * @param id the id to add
	 */
	public synchronized void add(long id) {
		
		if(id > highestId || highestId < 0) highestId = id;
		if(id < lowestId || lowestId < 0) lowestId = id;
		
		if(contains(id)) return;
		
		numIds++;
		
		IdBoundary curBoundary = idBoundary, lastBoundary = null;
		while(curBoundary != null) {
			if(id < curBoundary.boundaryStart) break;
			lastBoundary = curBoundary;
			// move the end of the boundary
			if(curBoundary.boundaryPostEnd == id) {
				curBoundary.boundaryPostEnd = id+1;
				// if the current and the next boundary are directly successive now, merge them
				if(curBoundary.next != null && curBoundary.boundaryPostEnd >= curBoundary.next.boundaryStart) {
					curBoundary.boundaryPostEnd = curBoundary.next.boundaryPostEnd;
					curBoundary.next = curBoundary.next.next;
				}
				return;
			}
			// move the start of the boundary
			if(curBoundary.boundaryStart == (id+1)) {
				curBoundary.boundaryStart = id;
				// current and preceding boundary can not be directly successive now, since a movement of the preceding boundary's end would have happened before
				return;
			}
			curBoundary = curBoundary.next;
		}
		// append new boundary
		curBoundary = new IdBoundary();
		if(lastBoundary == null) {
			curBoundary.next = this.idBoundary;
			this.idBoundary = curBoundary;
		} else {
			curBoundary.next = lastBoundary.next;
			lastBoundary.next = curBoundary;
		}
		curBoundary.boundaryStart = id;
		curBoundary.boundaryPostEnd = id+1;
		
	}
	
	/**
	 * @param id the id to look for
	 * @return true if the given id is included in this {@link IdCollection}, false otherwise
	 */
	public synchronized boolean contains(long id) {
		if(idBoundary == null) return false;
		IdBoundary curBoundary = idBoundary;
		do {
			if(curBoundary.boundaryStart <= id && id < curBoundary.boundaryPostEnd) return true;
		} while((curBoundary = curBoundary.next) != null);
		return false;
	}
	
	@Override
	public Iterator<IdBoundary> iterator() {
		return new Iterator<IdBoundary>() {
			
			private IdBoundary nextBoundary = IdCollection.this.idBoundary;
			
			@Override
			public boolean hasNext() {
				return nextBoundary != null;
			}

			@Override
			public IdBoundary next() {
				IdBoundary b = nextBoundary;
				nextBoundary = nextBoundary.next;
				return b;
			}

			@Override
			public void remove() {
				
			}
			
		};
	}
	
	/**
	 * @return the highest id included in this {@link IdCollection}
	 */
	public long getHighestId() {
		return highestId;
	}
	
	/**
	 * @return the lowest id included in this {@link IdCollection}
	 */
	public long getLowestId() {
		return lowestId;
	}
	
	/**
	 * @return the amount of different ids included in this {@link IdCollection}
	 */
	public long getNumIds() {
		return numIds;
	}
}
