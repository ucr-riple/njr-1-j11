package net.dtkanov.blocks.logic.derived;

import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.Wire;
/** Implements NOT-OR operation. */
public class NORNode extends Node {
	/** Input nodes. */
	private Node inNOT[];
	/** Output node. */
	private Node outAND = new ANDNode();
	/** Constructs a NOT-OR node. */
	public NORNode() {
		this(null);
	}
	/** Constructs a NOT-OR node with provided Wire. */
	public NORNode(Wire out) {
		super(out);
		inNOT[0] = new NOTNode().connectDst(0, outAND, 0);
		inNOT[1] = new NOTNode().connectDst(0, outAND, 1);
	}

	@Override
	public Node in(int index, boolean value) {
		propagate();
		if (index == 0 || index == 1) {
			inNOT[index].in(0, value);
		}
		return this;
	}

	@Override
	public boolean out(int index) {
		return outAND.out(index);
	}

	@Override
	public boolean isReady() {
		return inNOT[0].isReady() && inNOT[1].isReady();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		inNOT[0].propagate();
		inNOT[1].propagate();
		super.propagate(true);
	}

	@Override
	public void reset() {
		if (inNOT == null) {
			inNOT = new NOTNode[2];
			inNOT[0] = new NOTNode();
			inNOT[1] = new NOTNode();
		}
		inNOT[0].reset();
		inNOT[1].reset();
	}
}
