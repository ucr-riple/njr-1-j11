package net.dtkanov.blocks.logic;
/** AND logic gate */
public class ANDNode extends Node {
	/** Is input received? */
	protected boolean state[];
	/** Received input. */
	protected boolean data[] = new boolean[2];
	/** Constructs AND node without and connections. */
	public ANDNode() {
		super(null);
	}
	
	public ANDNode(Node op1, int port1, Node op2, int port2) {
		this();
		connectSrc(op1, port1, 0);
		connectSrc(op2, port2, 1);
	}

	public void reset() {
		if (state == null)
			state = new boolean[2];
		state[0] = false;
		state[1] = false;
	}

	/**
	 * @param index of the input, supports only 0 or 1
	 */
	@Override
	public Node in(int index, boolean value) {
		propagate();
		if (index == 0 || index == 1) {
			data[index] = value;
			state[index] = true;
		}
		return this;
	}

	@Override
	public boolean out(int index) {
		return data[0] && data[1];
	}

	@Override
	public boolean isReady() {
		return state[0] && state[1];
	}
}
