package net.dtkanov.blocks.logic;
/**
 * Abstract class representing a node in the circuit. It has inputs, output
 * and wire that connects it to other nodes.
 * @author akademi4eg
 *
 */
public abstract class Node {
	protected static int next_id = 1;
	protected int id;
	protected Wire out_node;
	/** @param out Wire connecting node outputs to other nodes. */
	protected Node(Wire out) {
		out_node = out;
		id = next_id++;
		reset();
	}
	/** @return ID of the node. */
	public int getId() {
		return id;
	}
	/** @return node's wire */
	public Wire getWire() {
		return out_node;
	}
	/** Removes connected output wires. */
	public Node disconnectDst() {
		out_node = null;
		return this;
	}
	/**
	 * Connects dst node to output of current node.
	 * @param src_port output port of current node
	 * @param dst node to be connected
	 * @param dst_port input port of dst_node
	 * @return pointer to current node
	 */
	public Node connectDst(int src_port, Node dst, int dst_port) {
		if (out_node == null)
			out_node = new Wire(this);
		out_node.addConnection(src_port, dst, dst_port);
		return this;
	}
	/**
	 * Connects src node to input of current node.
	 * @param src node to be connected
	 * @param src_port output port of src_node
	 * @param dst_port input port of current node
	 * @return pointer to current node
	 */
	public Node connectSrc(Node src, int src_port, int dst_port) {
		src.connectDst(src_port, this, dst_port);
		return this;
	}
	/**
	 * Propagates signal if node is ready. Stores input for node.
	 * @param index of the port
	 * @param value of the input
	 * @return pointer to current node
	 */
	public abstract Node in(int index, boolean value);
	/**
	 * Returns output for current state of node.
	 * Doesn't take into account node's readiness.
	 * @param index of the output port.
	 * @return pointer to current node
	 */
	public abstract boolean out(int index);
	/**
	 * Checks if all inputs are ready.
	 * @return true if all inputs received
	 */
	public abstract boolean isReady();
	/** Resets inputs state.
	 * isReady method should return false after this call.
	 */
	public abstract void reset();
	/** Attempts to propagate node's output via wire if node is ready. */
	public void propagate() {
		propagate(false);
	}
	/**
	 * Propagates node's output via wire.
	 * @param force ignores isReady call if is set to true
	 */
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		if (out_node != null)
			out_node.propagate();
		reset();
	}
	
	public String toString() {
		return "["+(isReady()?"+":"-")+getId()+":"+getClass().getSimpleName()+"]";
	}
	/** String representation of Node that takes into account its children.<br/>
	 *  <strong>Caution!</strong> <i>Call for circuits with cycles may cause stack overflow.</i>
	 */
	public String toStringDeep() {
		if (out_node != null)
			return toString()+System.getProperty("line.separator")+getWire().toStringDeep();
		else
			return toString();
	}
}