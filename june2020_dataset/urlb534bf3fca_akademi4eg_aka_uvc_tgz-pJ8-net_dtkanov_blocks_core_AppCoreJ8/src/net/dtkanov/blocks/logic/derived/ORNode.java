package net.dtkanov.blocks.logic.derived;

import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.Wire;

public class ORNode extends Node {
	private Node inNOT[];
	private Node outNOT;
	
	public ORNode() {
		this(null);
	}
	
	public ORNode(Wire out) {
		super(out);
		Node andNode = new ANDNode();
		inNOT[0] = new NOTNode().connectDst(0, andNode, 0);
		inNOT[1] = new NOTNode().connectDst(0, andNode, 1);
		outNOT = new NOTNode().connectSrc(andNode, 0, 0);
	}
	
	public ORNode(Node op1, int port1, Node op2, int port2) {
		this();
		connectSrc(op1, port1, 0);
		connectSrc(op2, port2, 1);
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
		return outNOT.out(index);
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
	
	@Override
	public String toString() {
		return "["+(isReady()?"+":"-")+getId()+":"+getClass().getSimpleName()+"|"+out(0)+"]";
	}
}
