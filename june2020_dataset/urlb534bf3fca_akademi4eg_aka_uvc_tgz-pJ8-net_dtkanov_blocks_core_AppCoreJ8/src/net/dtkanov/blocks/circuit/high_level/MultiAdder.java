package net.dtkanov.blocks.circuit.high_level;

import net.dtkanov.blocks.circuit.Adder;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;

public class MultiAdder extends Node {
	protected int bitness;
	protected Node data[];
	protected Node in_nops[];
	
	public MultiAdder(int num_bits) {
		super(null);
		bitness = num_bits;
		data = new Adder[bitness];
		in_nops = new NOPNode[2*bitness+1];
		for (int i = 0; i < bitness; i++)
			data[i] = new Adder();
		in_nops[0] = new NOPNode();
		in_nops[bitness] = new NOPNode();
		in_nops[2*bitness] = new NOPNode();
		data[0].connectSrc(in_nops[0], 0, 0)
			   .connectSrc(in_nops[bitness], 0, 1)
			   .connectSrc(in_nops[2*bitness], 0, 2);
		for (int i = 1; i < bitness; i++) {
			in_nops[i] = new NOPNode();
			in_nops[i+bitness] = new NOPNode();
			data[i].connectSrc(in_nops[i], 0, 0)
				   .connectSrc(in_nops[i+bitness], 0, 1)
				   .connectSrc(data[i-1], 1, 2);
		}
	}
	
	public int getBitness() {
		return bitness;
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
	public void reset() {
		if (in_nops != null) {
			for (Node n : in_nops)
				n.reset();
		}
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
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		for (Node n : in_nops)
			n.propagate();
		super.propagate(true);
	}
}
