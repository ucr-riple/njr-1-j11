package edu.concordia.dpis.commons;

// An exception raised when the node is dead
public class DeadNodeException extends Exception {

	private static final long serialVersionUID = 1L;

	public DeadNodeException() {
		super("Timed out");
	}

}
