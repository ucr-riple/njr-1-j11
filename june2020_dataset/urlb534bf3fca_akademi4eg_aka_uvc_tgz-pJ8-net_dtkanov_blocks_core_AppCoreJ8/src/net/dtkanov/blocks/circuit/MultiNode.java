package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.Wire;

public abstract class MultiNode extends Node {
	protected int bitness;
	protected Node data[] = null;
	
	public MultiNode(int num_bits) {
		this(num_bits, null);
	}
	
	public MultiNode(int num_bits, Wire out) {
		super(out);
		bitness = num_bits;
	}

	@Override
	public boolean isReady() {
		for (int i = 0; i < bitness; i++) {
			if (!data[i].isReady())
				return false;
		}
		return true;
	}

	@Override
	public void reset() {
		if (data != null) {
			for (int i = 0; i < bitness; i++)
				data[i].reset();
		}
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		for (int i = 0; i < bitness; i++)
			data[i].propagate();
		super.propagate(true);
	}
}
