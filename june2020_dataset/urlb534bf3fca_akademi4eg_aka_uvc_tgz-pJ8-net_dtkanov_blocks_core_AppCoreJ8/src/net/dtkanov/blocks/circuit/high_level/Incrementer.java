package net.dtkanov.blocks.circuit.high_level;

import net.dtkanov.blocks.circuit.HalfAdder;
import net.dtkanov.blocks.logic.ConstantNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;

public class Incrementer extends Node {
	protected int bitness;
	protected Node data[];
	protected Node in_nops[];

	public Incrementer(int num_bits) {
		super(null);
		bitness = num_bits;
		data = new HalfAdder[bitness];
		in_nops = new NOPNode[bitness];
		for (int i = 0; i < bitness; i++) {
			in_nops[i] = new NOPNode();
			data[i] = new HalfAdder();
			data[i].connectSrc(in_nops[i], 0, 0);
		}
		// carry in is always 1
		data[0].connectSrc(new ConstantNode(true)
								.connectSrc(in_nops[0], 0, 0), 0, 1);
		for (int i = 1; i < bitness; i++) {
			data[i].connectSrc(data[i-1], 1, 1);
		}
	}
	
	@Override
	public Node in(int index, boolean value) {
		in_nops[index].in(0, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		if (index == bitness)
			return data[bitness-1].out(1);
		else
			return data[index].out(0);
	}

	@Override
	public boolean isReady() {
		for (Node n : in_nops) {
			if (!n.isReady())
				return false;
		}
		return true;
	}

	@Override
	public void reset() {
		if (in_nops != null) {
			for (Node n : in_nops)
				n.reset();
		}
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		for (Node n : in_nops)
			n.propagate();
		super.propagate(true);
	}

}
