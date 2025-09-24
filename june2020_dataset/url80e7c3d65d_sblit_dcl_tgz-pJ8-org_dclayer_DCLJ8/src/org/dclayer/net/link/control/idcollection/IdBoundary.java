package org.dclayer.net.link.control.idcollection;

/**
 * a continuous block of ids. used in {@link IdCollection}.
 */
public class IdBoundary {
	/**
	 * the first id of that is included in this block
	 */
	public long boundaryStart = 0;
	/**
	 * the first id that is no longer included in this block
	 */
	public long boundaryPostEnd = 0;
	/**
	 * the next {@link IdBoundary} object after this one
	 */
	public IdBoundary next = null;
}
