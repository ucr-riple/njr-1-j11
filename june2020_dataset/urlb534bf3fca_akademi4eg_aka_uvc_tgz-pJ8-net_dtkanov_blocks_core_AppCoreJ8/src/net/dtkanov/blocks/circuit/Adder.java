package net.dtkanov.blocks.circuit;

import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.Wire;
import net.dtkanov.blocks.logic.derived.ORNode;
import net.dtkanov.blocks.logic.derived.XORNode;

public class Adder extends Node {
	private Node s_xor[];
	private Node cin_nop;
	private Node c_and[];
	private Node c_or;
	
	public Adder() {
		this(null);
	}
	
	public Adder(Wire out) {
		super(out);
		cin_nop.connectDst(0, s_xor[1], 0);
		s_xor[0].connectDst(0, s_xor[1], 1)
				.connectDst(0, c_and[0], 1);
		c_or.connectSrc(c_and[0], 0, 0)
			.connectSrc(c_and[1], 0, 1);
	}
	
	@Override
	public Node in(int index, boolean value) {
		if (index == 2) {// carry in
			cin_nop.in(0, value);
			c_and[0].in(0, value);
		} else {// args
			s_xor[0].in(index, value);
			c_and[1].in(index, value);
		}
		return this;
	}

	@Override
	public boolean out(int index) {
		if (index == 0)// sum
			return s_xor[1].out(0);
		else// carry out
			return c_or.out(0);
	}

	@Override
	public boolean isReady() {
		return s_xor[0].isReady() && cin_nop.isReady();
	}

	@Override
	public void reset() {
		if (s_xor == null) {
			s_xor = new XORNode[2];
			s_xor[0] = new XORNode();
			s_xor[1] = new XORNode();
		}
		if (cin_nop == null) {
			cin_nop = new NOPNode();
		}
		if (c_and == null) {
			c_and = new ANDNode[2];
			c_and[0] = new ANDNode();
			c_and[1] = new ANDNode();
		}
		if (c_or == null) {
			c_or = new ORNode();
		}
		s_xor[0].reset();
		s_xor[1].reset();
		cin_nop.reset();
		c_and[0].reset();
		c_and[1].reset();
		c_or.reset();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		cin_nop.propagate();
		s_xor[0].propagate();
		s_xor[1].propagate();
		c_and[0].propagate();
		c_and[1].propagate();
		c_or.propagate();
		super.propagate(true);
	}

}
