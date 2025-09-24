package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.Wire;

public class GatedDLatch extends Node {
	private Node outSR;
	private Node sigNOT;
	private Node sigNOP;
	private Node timeNOP;
	
	public GatedDLatch() {
		this(null);
	}
	
	public GatedDLatch(Wire out) {
		super(out);
		Node not_and = new ANDNode().connectSrc(sigNOT, 0, 0)
								    .connectSrc(timeNOP, 0, 1);
		Node nop_and = new ANDNode().connectSrc(sigNOP, 0, 0)
									.connectSrc(timeNOP, 0, 1);
		outSR = new SRLatch().connectSrc(not_and, 0, 0)//reset
							 .connectSrc(nop_and, 0, 1);//set
	}

	@Override
	public Node in(int index, boolean value) {
		if (index == 0) {// signal
			sigNOP.in(0, value);
			sigNOT.in(0, value);
		} else {// time
			timeNOP.in(0, value);
		}
		return this;
	}

	@Override
	public boolean out(int index) {
		return outSR.out(index);
	}

	@Override
	public boolean isReady() {
		return sigNOP.isReady() && sigNOT.isReady() && timeNOP.isReady();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		sigNOP.propagate();
		sigNOT.propagate();
		timeNOP.propagate();
		super.propagate(true);
	}

	@Override
	public void reset() {
		if (sigNOT == null)
			sigNOT = new NOTNode();
		if (sigNOP == null)
			sigNOP = new NOPNode();
		if (timeNOP == null)
			timeNOP = new NOPNode();
		sigNOT.reset();
		sigNOP.reset();
		timeNOP.reset();
	}

}
