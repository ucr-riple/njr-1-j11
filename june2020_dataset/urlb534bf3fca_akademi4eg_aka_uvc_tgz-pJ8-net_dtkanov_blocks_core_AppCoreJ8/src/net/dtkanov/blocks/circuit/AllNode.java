package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;

public class AllNode extends Node {
	protected Node in_nops[];
	protected Node ands[];
	
	public AllNode(int bitness) {
		super(null);
		in_nops = new NOPNode[bitness];
		ands = new ANDNode[bitness-1];// TODO Not optimal. Replace with tree.
		Node prev;
		in_nops[0] = new NOPNode();
		prev = in_nops[0];
		for (int i = 1; i < bitness; i++) {
			in_nops[i] = new NOPNode();
			ands[i-1] = new ANDNode();
			ands[i-1].connectSrc(prev, 0, 0);
			ands[i-1].connectSrc(in_nops[i], 0, 1);
			prev = ands[i-1];
		}
	}
	
	@Override
	public Node in(int index, boolean value) {
		in_nops[index].in(0, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		return ands[ands.length-1].out(0);
	}

	@Override
	public boolean isReady() {
		if (in_nops != null) {
			for (Node n : in_nops)
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
		for (Node n : in_nops) {
			n.propagate();
		}
		super.propagate(true);
	}

}
