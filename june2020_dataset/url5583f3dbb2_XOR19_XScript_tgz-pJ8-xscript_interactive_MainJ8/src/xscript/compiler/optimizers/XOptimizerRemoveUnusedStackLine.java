package xscript.compiler.optimizers;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import xscript.XOpcode;
import xscript.compiler.XOptimizer;
import xscript.compiler.inst.XInst;
import xscript.compiler.inst.XInst1B;

public class XOptimizerRemoveUnusedStackLine implements XOptimizer {

	@Override
	public boolean optimize(List<XInst> insts) {
		LinkedList<XInst> stackline = new LinkedList<XInst>();
		ListIterator<XInst> i = insts.listIterator();
		boolean b = false;
		while(i.hasNext()){
			XInst inst = i.next();
			
		}
		return b;
	}

}
