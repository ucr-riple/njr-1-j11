package xscript.compiler;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import xscript.XFlags;
import xscript.XOpcode;
import xscript.compiler.inst.XInst;
import xscript.compiler.inst.XInst1B;
import xscript.compiler.inst.XInst1S;
import xscript.compiler.inst.XInst1Sh;
import xscript.compiler.inst.XInstCall;
import xscript.compiler.inst.XInstDumyDel;
import xscript.compiler.inst.XInstJump;
import xscript.compiler.inst.XInstLine;
import xscript.compiler.inst.XInstVarDecl;
import xscript.compiler.inst.XInstVarLookup;
import xscript.compiler.optimizers.XOptimizerCombinePop;
import xscript.compiler.optimizers.XOptimizerDeleteDeadCode;
import xscript.compiler.tree.XTree;


public class XCodeGen{

	protected List<XInst> instructions = new ArrayList<XInst>();
	
	protected int stack;
	
	protected int stackstart;
	
	public XCodeGen() {
	}
	
	public XCodeGen(int stackstart) {
		this.stackstart = stackstart;
	}

	public void addInstruction(XTree t, XOpcode opcode){
		addInstruction(new XInst(t.position.position.line, opcode));
	}
	
	public void addInstruction(XTree t, XOpcode opcode, int i){
		addInstruction(new XInst1Sh(t.position.position.line, opcode, i));
	}
	
	public void addInstructionB(XTree t, XOpcode opcode, int i){
		addInstruction(new XInst1B(t.position.position.line, opcode, i));
	}
	
	public void addInstruction(XTree t, XOpcode opcode, XJumpTarget target){
		target.addJump();
		addInstruction(new XInstJump(t.position.position.line, opcode, target));
	}
	
	public void addInstruction(XTree t, XOpcode opcode, String s){
		addInstruction(new XInst1S(t.position.position.line, opcode, s));
	}
	
	public void addInstruction(XTree t, XVar var) {
		addInstruction(new XInstVarDecl(t.position.position.line, var));
	}
	
	public void addInstruction2(XTree t, XOpcode opcode, XVar var) {
		addInstruction(new XInstVarLookup(t.position.position.line, opcode, var));
	}
	
	public void addInstruction(XTree t, String[] kws, int unpackList, int unpackMap, int params) {
		addInstruction(new XInstCall(t.position.position.line, kws, unpackList, unpackMap, params));
	}
	
	public void addInstruction(XTree t, XJumpTarget target) {
		addInstruction(target.target = new XInstDumyDel());
	}
	
	public void addInstruction(XInst instruction){
		stack += instruction.getStackChange();
		instructions.add(instruction);
	}
	
	public void addInstructions(XCodeGen codeGen){
		stack += codeGen.stack;
		instructions.addAll(codeGen.instructions);
	}
	
	private void deleteDumies(){
		ListIterator<XInst> i = instructions.listIterator();
		while(i.hasNext()){
			XInst inst = i.next();
			if(inst instanceof XInstDumyDel){
				delete(inst);
				i.remove();
			}
		}
	}
	
	private void addLines(){
		ListIterator<XInst> i = instructions.listIterator();
		int line = -1;
		while(i.hasNext()){
			XInst inst = i.next();
			boolean addLine = inst.line!=line;
			if(!addLine){
				for(XInst inst2:instructions){
					if(inst2.line!=line && inst2.pointingTo(inst)){
						addLine = true;
						break;
					}
				}
			}
			if(addLine){
				i.previous();
				line = inst.line;
				XInstLine instLine = new XInstLine(inst.line);
				i.add(instLine);
				i.next();
				for(XInst inst2:instructions){
					if(inst2.line!=line){
						inst2.replace(this, inst, instLine, instructions);
					}
				}
			}
		}
	}
	
	/*private boolean deleteDeadCode(){
		boolean deleted;
		boolean b = false;
		do{
			deleted = false;
			boolean wasJump = false;
			ListIterator<XInst> i = instructions.listIterator();
			while(i.hasNext()){
				XInst inst = i.next();
				if(wasJump){
					for(XInst instr:instructions){
						if(instr.pointingTo(inst)){
							wasJump = false;
							break;
						}
					}
					if(wasJump){
						deleted = true;
						delete(inst);
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
							delete(inst);
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
	}*/
	
	public int resolve(XInst target) {
		if(target==null)
			return 0xFFFF;
		int i = instructions.indexOf(target);
		if(i==-1)
			return 0xFFFF;
		int size = 0;
		for(int j=0; j<i; j++){
			size += instructions.get(j).getSize();
		}
		return size;
	}
	
	private void resolve(){
		ListIterator<XInst> i = instructions.listIterator();
		while(i.hasNext()){
			XInst inst = i.next();
			inst.resolve(this, instructions);
		}
	}
	
	private void resolvePost(){
		ListIterator<XInst> i = instructions.listIterator();
		while(i.hasNext()){
			XInst inst = i.next();
			inst.resolvePost(this, instructions);
		}
	}
	
	private void replace(){
		ListIterator<XInst> i = instructions.listIterator();
		while(i.hasNext()){
			XInst inst = i.next();
			i.set(inst.replaceWith(this, instructions));
		}
	}
	
	private void delete(XInst instruction){
		ListIterator<XInst> i = instructions.listIterator();
		while(i.hasNext()){
			XInst inst = i.next();
			inst.delInst(this, instructions, instruction);
		}
	}
	
	private void replace(XInst instruction, XInst with){
		ListIterator<XInst> i = instructions.listIterator();
		while(i.hasNext()){
			XInst inst = i.next();
			inst.replace(this, instruction, with, instructions);
		}
	}
	
	/*private boolean makeEasy(){
		ListIterator<XInst> i = instructions.listIterator();
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
					for(XInst instr:instructions){
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
					delete(inst);
					i.remove();
					b=true;
				}
				if(n==0){
					inst = i.previous();
					delete(inst);
					i.remove();
				}else if(n==1){
					inst = i.previous();
					if(inst.getOpcode()!=XOpcode.POP_1){
						XInst newInst = new XInst(inst.line, XOpcode.POP_1);
						replace(inst, newInst);
						i.set(newInst);
					}
				}else{
					inst = i.previous();
					if(inst instanceof XInst1B){
						((XInst1B)inst).i = n;
					}else{
						XInst newInst = new XInst1B(inst.line, XOpcode.POP, n);
						replace(inst, newInst);
						i.set(newInst);
					}
				}
				if(!i.hasNext())
					break;
				inst = i.next();
			}
			if(inst.getOpcode()==XOpcode.RET && !i.hasNext()){
				delete(inst);
				i.remove();
				b=true;
			}
		}
		return b;
	}*/
	
	private int[] checkStackSize(){
		int[] sizes = new int[instructions.size()];
		Arrays.fill(sizes, -1);
		tryWay(0, stackstart, sizes);
		return sizes;
	}
	
	public void tryWay(int programPointer, int stackSize, int[] sizes){
		while(instructions.size()>programPointer){
			XInst inst = instructions.get(programPointer);
			int size = sizes[programPointer];
			stackSize += inst.getStackChange();
			if(stackSize<0){
				int i = 0;
				for(XInst instr:instructions){
					System.out.println(i+":\t"+sizes[i++]+"\t"+instr.toString());
				}
				throw new AssertionError("stacksize smaller than 0 at "+programPointer);
			}
			if(size==-1){
				sizes[programPointer] = size = stackSize;
			}else{
				if(size != stackSize){
					int i = 0;
					for(XInst instr:instructions){
						System.out.println(i+":\t"+sizes[i++]+"\t"+instr.toString());
					}
					throw new AssertionError("AT:"+programPointer);
				}
				return;
			}
			programPointer++;
			programPointer = inst.tryWay(this, programPointer, stackSize, sizes);
		}
		//if(stackSize!=1 && stackSize!=0){
			//if(comilerOptions.debugOutput){
				//System.out.println(instructions);
				//System.out.println(stackSize);
				//System.out.println(Arrays.toString(sizes));
			//}
			//throw new AssertionError();
		//}
	}
	
	private void compileSubparts(){
		for(XInst inst:instructions){
			inst.compileSubparts();
		}
	}
	
	public void generateFinalCode(XCompilerOptions options){
		ListWrapper listWrapper = new ListWrapper();
		List<XOptimizer> optimizers = new ArrayList<XOptimizer>();
		optimizers.add(new XOptimizerDeleteDeadCode());
		optimizers.add(new XOptimizerCombinePop());
		compileSubparts();
		deleteDumies();
		boolean didSomething;
		do{
			didSomething = false;
			for(XOptimizer optimizer:optimizers){
				didSomething |= optimizer.optimize(listWrapper);
			}
		}while(didSomething);
		/*boolean didSomething;
		do{
			didSomething = deleteDeadCode();
			didSomething |= makeEasy();
		}while(didSomething);*/
		if(!options.removeLines){
			addLines();
		}
		resolve();
		replace();
		int[]sizes = checkStackSize();
		resolvePost();
		if(XFlags.DEBUG){
			System.out.println("=========================================");
			System.out.println("               GEN                       ");
			System.out.println("=========================================");
			int i=0;
			for(XInst instr:instructions){
				System.out.println(sizes[i++]+"\t"+instr.toString());
			}
		}
	}
	
	public XInst[] getInstructions(){
		return instructions.toArray(new XInst[instructions.size()]);
	}
	
	public boolean isEmpty() {
		return instructions.isEmpty();
	}

	public List<XInst> getInstructionList() {
		return instructions;
	}
	
	public void getCode(XDataOutput dataOutput, XCompilerOptions options) {
		generateFinalCode(options);
		for(XInst i:instructions){
			i.toCode(dataOutput);
		}
	}
	
	private class ListWrapper extends AbstractList<XInst> {

		@Override
		public XInst get(int index) {
			return instructions.get(index);
		}

		@Override
		public int size() {
			return instructions.size();
		}

		@Override
		public boolean add(XInst e) {
			return instructions.add(e);
		}

		@Override
		public XInst set(int index, XInst element) {
			replace(instructions.get(index), element);
			return instructions.set(index, element);
		}

		@Override
		public void add(int index, XInst element) {
			instructions.add(index, element);
		}

		@Override
		public XInst remove(int index) {
			delete(instructions.get(index));
			return instructions.remove(index);
		}

		@Override
		public int indexOf(Object o) {
			return instructions.indexOf(o);
		}

		@Override
		public int lastIndexOf(Object o) {
			return instructions.lastIndexOf(o);
		}

		@Override
		public void clear() {
			instructions.clear();
		}

		@Override
		public boolean addAll(int index, Collection<? extends XInst> c) {
			return instructions.addAll(index, c);
		}

		@Override
		public boolean contains(Object o) {
			return instructions.contains(o);
		}

		@Override
		public Object[] toArray() {
			return instructions.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return instructions.toArray(a);
		}

		@Override
		public boolean remove(Object o) {
			if(o instanceof XInst)
				delete((XInst)o);
			return instructions.remove(o);
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return instructions.containsAll(c);
		}

		@Override
		public String toString() {
			return instructions.toString();
		}
		
	}
	
}
