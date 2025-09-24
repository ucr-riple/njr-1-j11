package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.ConstantNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;

/** Implements left/right shift. */
public class Shifter extends Node {
	protected int shift;
	protected int bitness;
	protected Node inNOPs[];
	protected Node outNOPs[];
	protected Node zero;
	
	public Shifter(int num_bits, int shift_len) {
		super(null);
		shift = shift_len;
		bitness = num_bits;
		zero = new ConstantNode(false);
		inNOPs = new NOPNode[num_bits];
		outNOPs = new NOPNode[num_bits+1];
		for (int i = 0; i < bitness; i++) {
			inNOPs[i] = new NOPNode();
			outNOPs[i] = new NOPNode();
		}
		// carry out
		outNOPs[bitness] = new NOPNode();
		// connection for init constant node,
		// eliminates explicit call of propagate()
		zero.connectSrc(inNOPs[0], 0, 0);
		int ind;
		for (int i = 0; i < bitness; i++) {
			ind = i - shift;
			if (ind > bitness-1 || ind < 0)
				outNOPs[i].connectSrc(zero, 0, 0);
			else
				outNOPs[i].connectSrc(inNOPs[ind], 0, 0);
		}
		ind = (bitness-shift)%bitness;
		if (shift<0)
			ind = (ind+bitness-1)%bitness;
		outNOPs[bitness].connectSrc(inNOPs[ind], 0, 0);
	}

	@Override
	public Node in(int index, boolean value) {
		inNOPs[index].in(0, value);
		return this;
	}
	/**
	 * @param 0 - (bitness-1) represent result of operation, bitness - carry flag
	 * @return
	 */
	@Override
	public boolean out(int index) {
		return outNOPs[index].out(0);
	}

	@Override
	public boolean isReady() {
		for (int i = 0; i < bitness; i++) {
			if (!inNOPs[i].isReady())
				return false;
		}
		return true;
	}

	@Override
	public void reset() {
		if (inNOPs != null) {
			for (int i = 0; i < bitness; i++) {
				inNOPs[i].reset();
			}
		}
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		for (int i = 0; i < bitness; i++) {
			inNOPs[i].propagate();
		}
		super.propagate(true);
	}
}
