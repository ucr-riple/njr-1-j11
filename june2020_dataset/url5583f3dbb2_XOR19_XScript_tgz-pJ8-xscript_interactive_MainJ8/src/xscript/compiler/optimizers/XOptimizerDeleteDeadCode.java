package xscript.compiler.optimizers;

import java.util.List;
import java.util.ListIterator;

import xscript.compiler.XOptimizer;
import xscript.compiler.inst.XInst;

public class XOptimizerDeleteDeadCode implements XOptimizer {

	@Override
	public boolean optimize(List<XInst> insts) {
		boolean deleted;
		boolean b = false;
		do{
			deleted = false;
			boolean wasJump = false;
			ListIterator<XInst> i = insts.listIterator();
			while(i.hasNext()){
				XInst inst = i.next();
				if(wasJump){
					for(XInst instr:insts){
						if(instr.pointingTo(inst)){
							wasJump = false;
							break;
						}
					}
					if(wasJump){
						deleted = true;
						i.remove();
					}
				}else{
					if(inst.isNormalJump()){
						wasJump = true;
						XInst next = null;
						if(i.hasNext()){
							next = i.next();
							i.previous();
							i.previous();
							i.next();
						}
						if(inst.pointingTo(next)){
							deleted = true;
							i.remove();
							wasJump = false;
						}
					}else if(inst.isAlwaysJump()){
						wasJump = true;
					}
				}
			}
			if(deleted)
				b=true;
		}while(deleted);
		return b;
	}

}
