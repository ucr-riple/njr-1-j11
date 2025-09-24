package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.derived.XORNode;

public class HalfAdder extends Node {
	private Node s_xor;
	private Node c_and;
	
	public HalfAdder() {
		super(null);
	}
	
	@Override
	public Node in(int index, boolean value) {
		s_xor.in(index, value);
		c_and.in(index, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		if (index == 0)// sum
			return s_xor.out(0);
		else// carry out
			return c_and.out(0);
	}

	@Override
	public boolean isReady() {
		return s_xor.isReady() && c_and.isReady();
	}

	@Override
	public void reset() {
		if (s_xor == null)
			s_xor = new XORNode();
		if (c_and == null)
			c_and = new ANDNode();
		s_xor.reset();
		c_and.reset();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		s_xor.propagate();
		c_and.propagate();
		super.propagate(true);
	}

}
