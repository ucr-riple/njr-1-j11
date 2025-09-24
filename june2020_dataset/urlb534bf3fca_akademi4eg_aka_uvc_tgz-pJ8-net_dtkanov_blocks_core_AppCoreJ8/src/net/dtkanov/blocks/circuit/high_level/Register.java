package net.dtkanov.blocks.circuit.high_level;

import net.dtkanov.blocks.circuit.GatedDLatch;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.Wire;

public class Register extends Node {
	private int bitness;
	private Node data[] = null;
	
	public Register(int num_bits) {
		this(num_bits, null);
	}
	
	public Register(int num_bits, Wire out) {
		super(out);
		bitness = num_bits;
		data = new GatedDLatch[bitness];
		for (int i = 0; i < bitness; i++)
			data[i] = new GatedDLatch();
	}

	@Override
	public Node in(int index, boolean value) {
		if (index < bitness) {
			// data
			data[index].in(0, value);
		} else {
			// clock
			for (int i = 0; i < bitness; i++) {
				data[i].in(1, value);
			}
		}
		return this;
	}

	@Override
	public boolean out(int index) {
		return data[index].out(0);
	}

	@Override
	public boolean isReady() {
		for (int i = 0; i < bitness; i++) {
			if (!data[i].isReady())
				return false;
		}
		return true;
	}

	@Override
	public void reset() {
		if (data != null) {
			for (int i = 0; i < bitness; i++)
				data[i].reset();
		}
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		for (int i = 0; i < bitness; i++)
			data[i].propagate();
		super.propagate(true);
	}
	
	public int getBitness() {
		return bitness;
	}

}
