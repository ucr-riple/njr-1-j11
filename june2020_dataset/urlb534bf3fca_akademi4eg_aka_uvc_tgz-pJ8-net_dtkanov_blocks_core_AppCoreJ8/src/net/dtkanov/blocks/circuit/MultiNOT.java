package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;

public class MultiNOT extends MultiNode {
	public MultiNOT(int num_bits) {
		super(num_bits);
		data = new NOTNode[bitness];
		for (int i = 0; i < bitness; i++)
			data[i] = new NOTNode();
	}

	@Override
	public Node in(int index, boolean value) {
		data[index].in(0, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		return data[index].out(0);
	}

}
