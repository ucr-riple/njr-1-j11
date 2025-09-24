package net.dtkanov.blocks.circuit.high_level.derived;

import net.dtkanov.blocks.circuit.high_level.Incrementer;
import net.dtkanov.blocks.circuit.high_level.MultiMux;
import net.dtkanov.blocks.circuit.high_level.Register;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.Node;

/** Controls changes of PC register. Requires initialization of PC,
 * because its values are used. */
public class PCRegController extends Node {
	protected Register PC;
	protected Node in_addr[];
	/** 00 => Set address, other values => add value to PC. */
	protected Node in_mode[];
	protected Node in_clock;
	protected MultiMux sel_add3_or_add2;
	protected MultiMux sel_add1_or_set;
	protected MultiMux sel_g1_or_g2;
	protected Incrementer inc[];
	
	public PCRegController(Register reg) {
		super(null);
		PC = reg;
		in_addr = new NOPNode[PC.getBitness()];
		for (int i = 0; i < PC.getBitness(); i++)
			in_addr[i] = new NOPNode();
		in_mode = new NOPNode[2];
		in_mode[0] = new NOPNode();
		in_mode[1] = new NOPNode();
		in_clock = new NOPNode();
		
		inc = new Incrementer[3];
		inc[0] = new Incrementer(PC.getBitness());// +1
		inc[1] = new Incrementer(PC.getBitness());// +2
		inc[2] = new Incrementer(PC.getBitness());// +3
		for (int i = 0; i < PC.getBitness(); i++) {
			PC.connectDst(i, inc[0], i);
			inc[0].connectDst(i, inc[1], i);
			inc[1].connectDst(i, inc[2], i);
		}
		in_clock.connectDst(0, PC, PC.getBitness());
		
		sel_add1_or_set = new MultiMux(PC.getBitness());
		sel_add1_or_set.connectSrc(in_mode[0], 0, 2*PC.getBitness());
		for (int i = 0; i < PC.getBitness(); i++) {
			sel_add1_or_set.connectSrc(inc[0], i, i);
			sel_add1_or_set.connectSrc(in_addr[i], i, i + PC.getBitness());
		}
		sel_add3_or_add2 = new MultiMux(PC.getBitness());
		sel_add3_or_add2.connectSrc(in_mode[0], 0, 2*PC.getBitness());
		for (int i = 0; i < PC.getBitness(); i++) {
			sel_add3_or_add2.connectSrc(inc[2], i, i);
			sel_add3_or_add2.connectSrc(inc[1], i, i + PC.getBitness());
		}
		sel_g1_or_g2 = new MultiMux(PC.getBitness());
		sel_g1_or_g2.connectSrc(in_mode[1], 0, 2*PC.getBitness());
		for (int i = 0; i < PC.getBitness(); i++) {
			sel_g1_or_g2.connectSrc(sel_add3_or_add2, i, i);
			sel_g1_or_g2.connectSrc(sel_add1_or_set, i, i + PC.getBitness());
			sel_g1_or_g2.connectDst(i, PC, i);
		}
	}
	
	@Override
	public Node in(int index, boolean value) {
		if (index < PC.getBitness()) {
			in_addr[index].in(0, value);
		} else if (index < PC.getBitness() + in_mode.length) {
			in_mode[index - PC.getBitness()].in(0, value);
		} else {
			in_clock.in(0, value);
		}
		return this;
	}

	@Override
	public boolean out(int index) {
		return sel_g1_or_g2.out(index);
	}

	@Override
	public boolean isReady() {
		for (Node n : in_addr)
			if (!n.isReady())
				return false;
		for (Node n : in_mode)
			if (!n.isReady())
				return false;
		return in_clock.isReady();
	}

	@Override
	public void reset() {
		if (in_addr != null) {
			for (Node n : in_addr)
				n.reset();
		}
		if (in_mode != null) {
			for (Node n : in_mode)
				n.reset();
		}
		if (in_clock != null)
			in_clock.reset();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
		for (Node n : in_addr)
			n.propagate();
		for (Node n : in_mode)
			n.propagate();
		in_clock.propagate();
		super.propagate(true);
	}

}
