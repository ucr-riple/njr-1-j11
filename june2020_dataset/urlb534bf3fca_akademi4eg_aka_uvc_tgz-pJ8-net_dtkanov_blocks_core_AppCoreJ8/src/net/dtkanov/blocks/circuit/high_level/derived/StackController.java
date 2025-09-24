package net.dtkanov.blocks.circuit.high_level.derived;

import net.dtkanov.blocks.circuit.Memory;
import net.dtkanov.blocks.circuit.high_level.Complementer;
import net.dtkanov.blocks.circuit.high_level.Incrementer;
import net.dtkanov.blocks.circuit.high_level.MultiMux;
import net.dtkanov.blocks.circuit.high_level.Register;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;
/** Controller for stack. */
public class StackController extends Node {
	protected Node in_data[];
	/** Mode. Set 1 for push. */
	protected Node in_op;
	protected Node in_clock;
	protected Register reg;
	protected Memory mem_low;
	protected Memory mem_high;
	protected Complementer neg;
	protected Complementer neg_neg_m1;
	protected Complementer neg_neg_m2;
	protected Incrementer inc_p1;
	protected Incrementer inc_p2;
	protected Incrementer inc_m1;
	protected Incrementer inc_m2;
	protected MultiMux sel_low;
	protected MultiMux sel_high;
	protected MultiMux sel_reg;
	
	public StackController(Register SP, Memory storage) {
		super(null);
		reg = SP;
		mem_low = new Memory(storage);
		mem_high = new Memory(storage);
		in_data = new NOPNode[reg.getBitness()];
		for (int i = 0; i < reg.getBitness(); i++) {
			in_data[i] = new NOPNode();
		}
		for (int i = 0; i < reg.getBitness()/2; i++) {
			in_data[i].connectDst(0, mem_low, reg.getBitness()+i);
			in_data[i+reg.getBitness()/2].connectDst(0, mem_high, reg.getBitness()+i);
		}
		in_op = new NOPNode();
		in_op.connectDst(0, mem_high, reg.getBitness()+Memory.BITS_IN_BYTE);
		in_op.connectDst(0, mem_low, reg.getBitness()+Memory.BITS_IN_BYTE);
		in_clock = new NOPNode();
		
		neg = new Complementer(reg.getBitness());
		neg_neg_m1 = new Complementer(reg.getBitness());
		neg_neg_m2 = new Complementer(reg.getBitness());
		inc_p1 = new Incrementer(reg.getBitness());
		inc_p2 = new Incrementer(reg.getBitness());
		inc_m1 = new Incrementer(reg.getBitness());
		inc_m2 = new Incrementer(reg.getBitness());
		for (int i = 0; i < reg.getBitness(); i++) {
			reg.connectDst(i, neg, i);
			reg.connectDst(i, inc_p1, i);
			inc_p1.connectDst(i, inc_p2, i);
			neg.connectDst(i, inc_m1, i);
			inc_m1.connectDst(i, neg_neg_m1, i);
			inc_m1.connectDst(i, inc_m2, i);
			inc_m2.connectDst(i, neg_neg_m2, i);
		}
		sel_low = new MultiMux(reg.getBitness());
		sel_low.connectSrc(in_op, 0, 2*reg.getBitness());
		for (int i = 0; i < reg.getBitness(); i++) {
			sel_low.connectSrc(neg_neg_m2, i, i);
			sel_low.connectSrc(reg, i, i+reg.getBitness());
			mem_low.connectSrc(sel_low, i, i);
		}
		sel_high = new MultiMux(reg.getBitness());
		sel_high.connectSrc(in_op, 0, 2*reg.getBitness());
		for (int i = 0; i < reg.getBitness(); i++) {
			sel_high.connectSrc(neg_neg_m1, i, i);
			sel_high.connectSrc(inc_p1, i, i+reg.getBitness());
			mem_high.connectSrc(sel_high, i, i);
		}
		sel_reg = new MultiMux(reg.getBitness());
		sel_reg.connectSrc(in_op, 0, 2*reg.getBitness());
		for (int i = 0; i < reg.getBitness(); i++) {
			sel_reg.connectSrc(neg_neg_m2, i, i);
			sel_reg.connectSrc(inc_p2, i, i+reg.getBitness());
			reg.connectSrc(sel_reg, i, i);
		}
		reg.connectSrc(in_clock, 0, reg.getBitness());
	}
	
	@Override
	public Node in(int index, boolean value) {
		if (index < reg.getBitness())
			in_data[index].in(0, value);
		else if (index < reg.getBitness()+1)
			in_op.in(0, value);
		else
			in_clock.in(0, value);
		return this;
	}

	@Override
	public boolean out(int index) {
		if (index < reg.getBitness()/2)
			return mem_low.out(index);
		else
			return mem_high.out(index-reg.getBitness()/2);
	}

	@Override
	public boolean isReady() {
		for (Node n : in_data)
			if (!n.isReady())
				return false;
		return in_op.isReady() && in_clock.isReady();
	}

	@Override
	public void reset() {
		if (in_data != null) {
			for (Node n : in_data)
				n.reset();
		}
		if (in_op != null)
			in_op.reset();
		if (in_clock != null)
			in_clock.reset();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		for (Node n : in_data)
			n.propagate();
		in_op.propagate();
		in_clock.propagate();
		super.propagate(true);
	}

}
