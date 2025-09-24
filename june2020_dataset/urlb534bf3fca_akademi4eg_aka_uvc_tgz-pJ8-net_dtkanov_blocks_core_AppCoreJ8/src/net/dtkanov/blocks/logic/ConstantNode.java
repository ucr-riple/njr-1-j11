package net.dtkanov.blocks.logic;
/** Node always sending same output. */
public class ConstantNode extends Node {
	/** Current value of the node */
	private boolean value;
	/**
	 * Constructs a node always returning value given.
	 * @param const_val
	 */
	public ConstantNode(boolean const_val) {
		super(null);
		value = const_val;
	}
	/**
	 * Changes value returning by this node.
	 * @param val new value
	 * @return pointer to current node
	 */
	public ConstantNode setValue(boolean val) {
		value = val;
		return this;
	}
	/** Stores nothing. Calls propagate(). */
	@Override
	public Node in(int index, boolean value) {
		propagate();
		return this;
	}

	@Override
	public boolean out(int index) {
		return value;
	}
	/**
	 * Always returns true.
	 * @return true
	 */
	@Override
	public boolean isReady() {
		return true;
	}

	/** Does nothing. */
	@Override
	public void reset() {
	}
}
