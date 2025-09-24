package net.dtkanov.blocks.circuit.high_level.derived;

import net.dtkanov.blocks.circuit.Memory;
import net.dtkanov.blocks.circuit.high_level.Extractor;
import net.dtkanov.blocks.logic.ConstantNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;
/** Represents CPU. */
public class CPU extends Node {
	/** Pointer size. */
	public static final int ADDR_SIZE = 16;
	protected byte storage[];
	protected ControlUnit cu;
	protected Extractor ext;
	protected Node in_clock;
	protected Node zero;
	
	public CPU() {
		super(null);
		zero = new ConstantNode(false);
		storage = new byte[1<<ADDR_SIZE];
		cu = new ControlUnit();
		ext = new Extractor(ADDR_SIZE, storage);
		for (int i = 0; i < Memory.BITS_IN_BYTE; i++) {
			ext.connectSrc(cu, i, i);
			ext.connectSrc(cu, i+ControlUnit.BITNESS, i+ControlUnit.BITNESS);
			ext.connectDst(i, cu, i);
			ext.connectDst(i+ControlUnit.BITNESS, cu, i+ControlUnit.BITNESS);
			ext.connectDst(i+ControlUnit.BITNESS*2, cu, i+ControlUnit.BITNESS*2);
			// for init
			ext.connectSrc(zero, 0, i);
			ext.connectSrc(zero, 0, i+ControlUnit.BITNESS);
		}
		in_clock = new NOPNode();
		in_clock.connectDst(0, cu, ControlUnit.BITNESS*3);
	}
	
	/** Call this after finishing writing data to storage. */
	public void init() {
		zero.propagate();
	}
	
	@Override
	public Node in(int index, boolean value) {
		in_clock.in(0, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		// nothing to return
		return false;
	}

	@Override
	public boolean isReady() {
		return in_clock.isReady();
	}

	@Override
	public void reset() {
		if (in_clock != null)
			in_clock.reset();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		in_clock.propagate();
		ext.propagate(true);
		super.propagate(true);
	}

	public ControlUnit getControlUnit() {
		return cu;
	}
	
	public void writeToMemory(int addr, int value) {
		storage[addr] = (byte) value;
	}
	
	public byte readFromMemory(int addr) {
		return storage[addr];
	}
	
}
