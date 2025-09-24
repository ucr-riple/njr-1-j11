package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.derived.ORNode;
import net.dtkanov.blocks.logic.Node;
/** Vectorized version of ORNode. */
public class MultiOR extends MultiNode {
	public MultiOR(int num_bits) {
		super(num_bits);
		data = new ORNode[bitness];
		for (int i = 0; i < bitness; i++)
			data[i] = new ORNode();
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
