package net.dtkanov.blocks.logic.derived;

import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.Wire;

public class NANDNode extends Node {
	private Node inAND;
	private Node outNOT = new NOTNode();
	
	public NANDNode() {
		this(null);
	}
	
	public NANDNode(Wire out) {
		super(out);
		outNOT.connectSrc(inAND, 0, 0);
	}

	@Override
	public Node in(int index, boolean value) {
		if (isReady())
			propagate();
		inAND.in(index, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		return outNOT.out(index);
	}

	@Override
	public boolean isReady() {
		return inAND.isReady();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		inAND.propagate();
		super.propagate(true);
	}

	@Override
	public void reset() {
		if (inAND == null)
			inAND = new ANDNode();
		inAND.reset();
	}
}
