package net.dtkanov.blocks.circuit.high_level;

import net.dtkanov.blocks.circuit.Memory;
import net.dtkanov.blocks.logic.ConstantNode;
import net.dtkanov.blocks.logic.Node;
/** Extracts three consecutive bytes from memory. */
public class Extractor extends Node {
	protected int bitness;
	protected Memory mem_ctrl;
	protected Memory mem_d1;
	protected Memory mem_d2;
	protected Node inc1;
	protected Node inc2;
	protected ConstantNode read_mode;
	
	public Extractor(int addr_size, byte[] storage) {
		super(null);
		bitness = addr_size;
		mem_ctrl = new Memory(bitness, storage);
		mem_d1 = new Memory(mem_ctrl);
		mem_d2 = new Memory(mem_ctrl);
		inc1 = new Incrementer(addr_size);
		inc2 = new Incrementer(addr_size);
		read_mode = new ConstantNode(false);
		for (int i = 0; i < bitness; i++) {
			inc1.connectDst(i, mem_d1, i);
			inc1.connectDst(i, inc2, i);
			inc2.connectDst(i, mem_d2, i);
		}
		for (int i = 0; i < Memory.BITS_IN_BYTE; i++) {
			mem_ctrl.connectSrc(read_mode, 0, bitness+i);
			mem_d1.connectSrc(read_mode, 0, bitness+i);
			mem_d2.connectSrc(read_mode, 0, bitness+i);
		}
		mem_ctrl.connectSrc(read_mode, 0, bitness+Memory.BITS_IN_BYTE);
		mem_d1.connectSrc(read_mode, 0, bitness+Memory.BITS_IN_BYTE);
		mem_d2.connectSrc(read_mode, 0, bitness+Memory.BITS_IN_BYTE);
	}
	
	@Override
	public Node in(int index, boolean value) {
		mem_ctrl.in(index, value);
		inc1.in(index, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		if (index < Memory.BITS_IN_BYTE)
			return mem_ctrl.out(index);
		else if (index < 2*Memory.BITS_IN_BYTE)
			return mem_d1.out(index-Memory.BITS_IN_BYTE);
		else
			return mem_d2.out(index-2*Memory.BITS_IN_BYTE);
	}

	@Override
	public boolean isReady() {
		return inc1.isReady();
	}

	@Override
	public void reset() {
		if (inc1 != null) {
			inc1.reset();
		}
	}

	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		read_mode.propagate(true);
		mem_ctrl.propagate();
		inc1.propagate();
		super.propagate(true);
	}
}
