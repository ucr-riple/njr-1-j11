package net.dtkanov.blocks.circuit.high_level;

import net.dtkanov.blocks.circuit.Mux;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;
/** Vectorized version of Mux with shared control. */
public class MultiMux extends Node {
	protected Node csNOP;
	protected Node inNOPs[];
	protected Node data[];
	protected int bitness;
	public MultiMux(int num_bits) {
		super(null);
		bitness = num_bits;
		data = new Mux[bitness];
		csNOP = new NOPNode();
		inNOPs = new NOPNode[bitness*2];
		for (int i = 0; i < bitness; i++) {
			inNOPs[i] = new NOPNode();
			inNOPs[i+bitness] = new NOPNode();
			data[i] = new Mux();
			data[i].connectSrc(inNOPs[i], 0, 0)
				.connectSrc(inNOPs[i+bitness], 0, 1)
				.connectSrc(csNOP, 0, 2);
		}
	}
	@Override
	public Node in(int index, boolean value) {
		if (index < 2*bitness)
			inNOPs[index].in(0, value);
		else
			csNOP.in(0, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		return data[index].out(0);
	}
	
	@Override
	public boolean isReady() {
		for (Node n : inNOPs) {
			if (!n.isReady())
				return false;
		}
		return csNOP.isReady();
	}
	
	@Override
	public void reset() {
		if (csNOP != null)
			csNOP.reset();
		if (inNOPs != null) {
			for (Node n : inNOPs)
				n.reset();
		}
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		csNOP.propagate();
		for (Node n : inNOPs)
			n.propagate();
		super.propagate(true);
	}

}