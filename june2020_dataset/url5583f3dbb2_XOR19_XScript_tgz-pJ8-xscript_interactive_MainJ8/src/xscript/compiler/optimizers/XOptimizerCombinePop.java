package xscript.compiler.optimizers;

import java.util.List;
import java.util.ListIterator;

import xscript.XOpcode;
import xscript.compiler.XOptimizer;
import xscript.compiler.inst.XInst;
import xscript.compiler.inst.XInst1B;

public class XOptimizerCombinePop implements XOptimizer {

	@Override
	public boolean optimize(List<XInst> insts) {
		ListIterator<XInst> i = insts.listIterator();
		boolean b = false;
		while(i.hasNext()){
			XInst inst = i.next();
			if(inst.getOpcode()==XOpcode.POP_1 || (inst.getOpcode()==XOpcode.POP && inst instanceof XInst1B)){
				int n = -inst.getStackChange();
				while(i.hasNext()){
					inst = i.next();
					if(inst.getOpcode()!=XOpcode.POP && inst.getOpcode()!=XOpcode.POP_1){
						i.previous();
						break;
					}
					boolean isTarget = false;
					for(XInst instr:insts){
						if(instr.pointingTo(inst)){
							isTarget = true;
							break;
						}
					}
					if(isTarget){
						i.previous();
						break;
					}
					n -= inst.getStackChange();
					i.remove();
					b=true;
				}
				if(n==0){
					inst = i.previous();
					i.remove();
				}else if(n==1){
					inst = i.previous();
					if(inst.getOpcode()!=XOpcode.POP_1){
						XInst newInst = new XInst(inst.line, XOpcode.POP_1);
						i.set(newInst);
					}
				}else{
					inst = i.previous();
					if(inst instanceof XInst1B){
						((XInst1B)inst).i = n;
					}else{
						XInst newInst = new XInst1B(inst.line, XOpcode.POP, n);
						i.set(newInst);
					}
				}
				if(!i.hasNext())
					break;
				inst = i.next();
			}
			if(inst.getOpcode()==XOpcode.RET && !i.hasNext()){
				i.remove();
				b=true;
			}
		}
		return b;
	}

}
