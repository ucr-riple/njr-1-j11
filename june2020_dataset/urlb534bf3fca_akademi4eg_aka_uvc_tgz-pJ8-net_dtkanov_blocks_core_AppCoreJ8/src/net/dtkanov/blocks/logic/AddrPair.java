package net.dtkanov.blocks.logic;

/** Specifies node and port pair */
public class AddrPair {
	private Node node;
	private int port;
	/**
	 * Construct node-port pair.
	 * @param dst_node node part
	 * @param dst_port port of that node
	 */
	public AddrPair(Node dst_node, int dst_port) {
		node = dst_node;
		port = dst_port;
	}
	/**
	 * @return node for the pair
	 */
	public Node getNode() {
		return node;
	}
	/**
	 * @return port for the pair
	 */
	public int getPort() {
		return port;
	}
}