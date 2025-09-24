package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.ConstantNode;
import net.dtkanov.blocks.logic.Node;
/** Implements ROM. */
public class LookUp extends Memory {
	protected Node zero;
	
	public LookUp(int pointer_size, byte[] a_storage) {
		super(pointer_size, a_storage);
		zero = new ConstantNode(false);
		for (int i = 0; i < in_data.length; i++)
			zero.connectDst(0, in_data[i], 0);
		zero.connectDst(0, in_mode, 0);
	}
	
	@Override
	public Node in(int index, boolean value) {
		if (index < in_addr.length)
			in_addr[index].in(0, value);
		return this;
	}
	
	@Override
	public boolean isReady() {
		for (Node n : in_addr)
			if (!n.isReady())
				return false;
		return true;
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		zero.propagate();
		super.propagate(true);
	}
}
