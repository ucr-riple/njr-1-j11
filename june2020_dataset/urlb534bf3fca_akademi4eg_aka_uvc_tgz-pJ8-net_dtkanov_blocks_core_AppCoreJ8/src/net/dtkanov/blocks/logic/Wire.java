package net.dtkanov.blocks.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
/** Represents wire connecting input node to output nodes. */
public class Wire {
	/** Input node. */
	private Node in_node;
	/** Map of output node and port to input port. */
	private Map<AddrPair, Integer> out_node;
	/**
	 * Constructs a wire with given input node.
	 * @param src input node
	 */
	public Wire(Node src) {
		Map<AddrPair, Integer> data = new HashMap<AddrPair, Integer>();
		in_node = src;
		out_node = data;
	}
	/**
	 * Constructs a wire with given input and output nodes.
	 * @param in input node
	 * @param out map of output node and port to input port
	 */
	public Wire(Node in, Map<AddrPair, Integer> out) {
		in_node = in;
		out_node = out;
	}
	/**
	 * Constructs a wire with given input and output nodes.
	 * @param src input node
	 * @param src_port input node port
	 * @param dst output node
	 * @param dst_port output node port
	 */
	public Wire(Node src, int src_port, Node dst, int dst_port) {
		Map<AddrPair, Integer> data = new HashMap<AddrPair, Integer>();
		data.put(new AddrPair(dst, dst_port), src_port);
		in_node = src;
		out_node = data;
	}
	/**
	 * Adds input-output connection.
	 * @param src_port port of the input
	 * @param dst output node
	 * @param dst_port output node port
	 */
	public void addConnection(int src_port, Node dst, int dst_port) {
		out_node.put(new AddrPair(dst, dst_port), src_port);
	}
	/**
	 * Propagates input node output to output nodes
	 * and calls propagate for them().
	 */
	public void propagate() {
		for (Entry<AddrPair, Integer> data : out_node.entrySet()) {
			int port = data.getValue();
			AddrPair addr = data.getKey();
			addr.getNode().in(addr.getPort(), in_node.out(port));
			addr.getNode().propagate();
		}
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Entry<AddrPair, Integer> data : out_node.entrySet()) {
			int port = data.getValue();
			AddrPair addr = data.getKey();
			str.append("("+port+") -> " + addr.getNode() + "("+addr.getPort()+")"+System.getProperty("line.separator"));
		}
		return str.toString();
	}
	/** String representation of wire and its output nodes.<br/>
	 *  <strong>Caution!</strong> <i>Call for circuits with cycles may cause stack overflow.</i>
	 */
	public String toStringDeep() {
		StringBuilder str = new StringBuilder(toString());
		for (AddrPair addr : out_node.keySet())
			str.append(addr.getNode().toStringDeep()+System.getProperty("line.separator"));
		return str.toString();
	}
}
