package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;

/** Implements left/right rotation. */
public class Rotator extends Node {
	protected int shift;
	protected int bitness;
	protected Node inNOPs[];
	protected Node outNOPs[];
	protected Node zero;
	
	public Rotator(int num_bits, int shift_len) {
		super(null);
		shift = shift_len;
		bitness = num_bits;
		inNOPs = new NOPNode[num_bits];
		outNOPs = new NOPNode[num_bits];
		for (int i = 0; i < bitness; i++) {
			inNOPs[i] = new NOPNode();
			outNOPs[i] = new NOPNode();
		}
		for (int i = 0; i < bitness; i++) {
			int ind = (bitness + i - shift) % bitness;
			outNOPs[i].connectSrc(inNOPs[ind], 0, 0);
		}
	}

	@Override
	public Node in(int index, boolean value) {
		inNOPs[index].in(0, value);
		return this;
	}

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
