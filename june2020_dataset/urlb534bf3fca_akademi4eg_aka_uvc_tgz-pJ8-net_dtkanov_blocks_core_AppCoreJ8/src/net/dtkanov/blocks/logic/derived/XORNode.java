package net.dtkanov.blocks.logic.derived;

import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.Wire;

public class XORNode extends Node {
	private Node inNOT[];
	private Node inNOP[];
	private Node outOR = new ORNode();
	
	public XORNode() {
		this(null);
	}
	
	public XORNode(Wire out) {
		super(out);
		Node andNode1 = new ANDNode();
		Node andNode2 = new ANDNode();
		// notA & B
		inNOT[0] = new NOTNode().connectDst(0, andNode1, 0);
		inNOP[1] = new NOPNode().connectDst(0, andNode1, 1);
		// notB & A
		inNOT[1] = new NOTNode().connectDst(0, andNode2, 1);
		inNOP[0] = new NOPNode().connectDst(0, andNode2, 0);
		outOR.connectSrc(andNode1, 0, 0).connectSrc(andNode2, 0, 1);
	}
	
	@Override
	public Node in(int index, boolean value) {
		propagate();
		if (index == 0 || index == 1) {
			inNOT[index].in(0, value);
			inNOP[index].in(0, value);
		}
		return this;
	}
	
	@Override
	public boolean out(int index) {
		return outOR.out(index);
	}

	@Override
	public boolean isReady() {
		return inNOT[0].isReady() && inNOT[1].isReady()
				&& inNOP[0].isReady() && inNOP[1].isReady();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		inNOT[0].propagate();
		inNOP[0].propagate();
		inNOT[1].propagate();
		inNOP[1].propagate();
		super.propagate(true);
	}

	@Override
	public void reset() {
		if (inNOT == null) {
			inNOT = new NOTNode[2];
			inNOT[0] = new NOTNode();
			inNOT[1] = new NOTNode();
		}
		if (inNOP == null) {
			inNOP = new NOPNode[2];
			inNOP[0] = new NOPNode();
			inNOP[1] = new NOPNode();
		}
		inNOT[0].reset();
		inNOP[0].reset();
		inNOT[1].reset();
		inNOP[1].reset();
	}
	
	@Override
	public String toString() {
		return "["+(isReady()?"+":"-")+getId()+":"+getClass().getSimpleName()+"|"+out(0)+"]";
	}
}
