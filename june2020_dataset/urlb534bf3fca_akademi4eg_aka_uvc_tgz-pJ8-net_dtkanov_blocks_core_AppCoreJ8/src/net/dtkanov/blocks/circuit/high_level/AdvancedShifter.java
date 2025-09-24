package net.dtkanov.blocks.circuit.high_level;

import net.dtkanov.blocks.circuit.Shifter;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;
/** Shifts left or right on arbitrary number of bits. */
public class AdvancedShifter extends Node {
	protected int bitness;
	protected Node shifters[];
	protected Node muxs[];
	protected Node inNOPs[];
	protected Node ctrlNOPs[];
	
	public AdvancedShifter(int num_bits, boolean is_left) {
		super(null);
		bitness = num_bits;
		int num_ctrls = (int)Math.ceil(Math.log(num_bits+0.1)/Math.log(2));
		inNOPs = new NOPNode[num_bits];
		ctrlNOPs = new NOPNode[num_ctrls];
		shifters = new Shifter[num_ctrls];
		muxs = new MultiMux[num_ctrls];
		for (int i = 0; i < num_bits; i++) {
			inNOPs[i] = new NOPNode();
		}
		for (int i = 0; i < num_ctrls; i++) {
			if (is_left)
				shifters[i] = new Shifter(num_bits, 1<<i);
			else
				shifters[i] = new Shifter(num_bits, -(1<<i));
			// additional bit for carry
			muxs[i] = new MultiMux(num_bits+1);
			ctrlNOPs[i] = new NOPNode();
		}
		for (int i = 1; i < num_ctrls; i++) {
			for (int j = 0; j < bitness; j++) {
				shifters[i].connectSrc(muxs[i-1], j, j);
				muxs[i].connectSrc(shifters[i], j, j);
				muxs[i].connectSrc(muxs[i-1], j, j+bitness+1);
			}
			muxs[i].connectSrc(shifters[i], bitness, bitness);
			muxs[i].connectSrc(muxs[i-1], bitness, 2*bitness+1);
			muxs[i].connectSrc(ctrlNOPs[i], 0, 2*bitness+2);
		}
		for (int j = 0; j < bitness; j++) {
			shifters[0].connectSrc(inNOPs[j], 0, j);
			muxs[0].connectSrc(shifters[0], j, j);
			muxs[0].connectSrc(inNOPs[j], 0, j+bitness+1);
		}
		muxs[0].connectSrc(shifters[0], bitness, bitness);
		muxs[0].connectSrc(inNOPs[0], 0, 2*bitness+1);
		muxs[0].connectSrc(ctrlNOPs[0], 0, 2*bitness+2);
	}
	
	public int countCtrlBits() {
		return ctrlNOPs.length;
	}
	
	@Override
	public Node in(int index, boolean value) {
		if (index < bitness) {
			inNOPs[index].in(0, value);
		} else {
			ctrlNOPs[index-bitness].in(0, value);
		}
		return this;
	}

	@Override
	public boolean out(int index) {
		return muxs[muxs.length-1].out(index);
	}

	@Override
	public boolean isReady() {
		for (Node n : inNOPs)
			if (!n.isReady())
				return false;
		for (Node n : ctrlNOPs)
			if (!n.isReady())
				return false;
		return true;
	}

	@Override
	public void reset() {
		if (inNOPs != null) {
			for (Node n : inNOPs)
				n.reset();
		}
		if (ctrlNOPs != null) {
			for (Node n : ctrlNOPs)
				n.reset();
		}
	}

	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		for (Node n : inNOPs)
			n.propagate();
		for (Node n : ctrlNOPs)
			n.propagate();
		super.propagate(true);
	}
}
