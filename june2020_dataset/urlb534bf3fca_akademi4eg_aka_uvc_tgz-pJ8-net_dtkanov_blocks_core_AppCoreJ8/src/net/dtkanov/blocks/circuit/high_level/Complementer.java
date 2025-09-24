package net.dtkanov.blocks.circuit.high_level;

import net.dtkanov.blocks.circuit.MultiNOT;
import net.dtkanov.blocks.logic.Node;

public class Complementer extends MultiNOT {
	protected Node inc = null;
	
	public Complementer(int num_bits) {
		super(num_bits);
		inc = new Incrementer(num_bits);
		for (int i = 0; i < bitness; i++) {
			data[i].connectDst(0, inc, i);
		}
	}
	
	@Override
	public boolean out(int index) {
		return inc.out(index);
	}
	
	@Override
	public void reset() {
		super.reset();
		if (inc != null)
			inc.reset();
	}
}
