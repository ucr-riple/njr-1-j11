package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;
/** 1-to-2 demultiplexer. */
public class DeMux extends Node {
	/** Inputs. */
	private Node inNOP;
	/** Part of control. */
	private Node csNOP;
	/** Part of control. */
	private Node csNOT;
	/** Outputs. */
	private Node outANDs[];
	
	public DeMux() {
		super(null);
		outANDs = new ANDNode[2];
		outANDs[0] = new ANDNode();
		outANDs[1] = new ANDNode();
		outANDs[0].connectSrc(csNOP, 0, 0)
				  .connectSrc(inNOP, 0, 1);
		outANDs[1].connectSrc(csNOT, 0, 0)
		  		  .connectSrc(inNOP, 0, 1);
	}
	/**
	 * Set index==1 for control signal. Control==1 outputs value to index==0 out.
	 */
	@Override
	public Node in(int index, boolean value) {
		if (index == 1) {
			csNOP.in(0, value);
			csNOT.in(0, value);
		} else {
			inNOP.in(0, value);
		}
		return this;
	}

	@Override
	public boolean out(int index) {
		return outANDs[index].out(0);
	}

	@Override
	public boolean isReady() {
		return inNOP.isReady()
				&& csNOP.isReady() && csNOT.isReady();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		inNOP.propagate();
		csNOP.propagate();
		csNOT.propagate();
		super.propagate(true);
	}

	@Override
	public void reset() {
		if (inNOP == null) {
			inNOP = new NOPNode();
		}
		if (csNOP == null)
			csNOP = new NOPNode();
		if (csNOT == null)
			csNOT = new NOTNode();
		inNOP.reset();
		csNOP.reset();
		csNOT.reset();
	}

}
