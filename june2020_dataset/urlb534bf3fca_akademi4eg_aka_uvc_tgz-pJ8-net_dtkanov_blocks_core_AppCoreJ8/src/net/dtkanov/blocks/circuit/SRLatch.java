package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.Wire;
import net.dtkanov.blocks.logic.derived.NORNode;

public class SRLatch extends Node {
	private Node sNOR;
	private Node rNOR;

	public SRLatch() {
		this(null);
	}
	
	public SRLatch(Wire out) {
		super(out);
		sNOR.connectDst(0, rNOR, 1);
		rNOR.connectDst(0, sNOR, 1);
		sNOR.in(1, false);
		rNOR.in(1, false);
	}
	
	@Override
	public Node in(int index, boolean value) {
		propagate();
		if (index == 0) {// R
			rNOR.in(0, value);
		} else {// S
			sNOR.in(0, value);
		}
		return this;
	}

	@Override
	public boolean out(int index) {
		if (index == 0) {// Q
			return rNOR.out(0);
		} else {// notQ
			return sNOR.out(0);
		}
	}

	@Override
	public boolean isReady() {
		return sNOR.isReady() && rNOR.isReady();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		sNOR.propagate();
		rNOR.propagate();
		sNOR.propagate(true);
		rNOR.propagate(true);
		super.propagate(true);
	}

	@Override
	public void reset() {
		if (sNOR == null)
			sNOR = new NORNode();
		if (rNOR == null)
			rNOR = new NORNode();
		sNOR.reset();
		rNOR.reset();
		sNOR.in(1, rNOR.out(0));
		rNOR.in(1, sNOR.out(0));
	}
}