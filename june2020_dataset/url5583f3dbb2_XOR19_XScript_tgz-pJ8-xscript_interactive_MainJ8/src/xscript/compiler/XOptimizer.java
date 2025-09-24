package xscript.compiler;

import java.util.List;

import xscript.compiler.inst.XInst;

public interface XOptimizer {

	public boolean optimize(List<XInst> insts);
	
}
