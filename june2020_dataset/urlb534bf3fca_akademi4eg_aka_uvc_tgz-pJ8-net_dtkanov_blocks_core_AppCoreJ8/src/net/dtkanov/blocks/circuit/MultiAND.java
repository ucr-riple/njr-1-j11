package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.Node;

public class MultiAND extends MultiNode {
	public MultiAND(int num_bits) {
		super(num_bits);
		data = new ANDNode[bitness];
		for (int i = 0; i < bitness; i++)
			data[i] = new ANDNode();
	}

	@Override
	public Node in(int index, boolean value) {
		if (index < bitness)
			data[index].in(0, value);
		else
			data[index-bitness].in(1, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		return data[index].out(0);
	}

}
