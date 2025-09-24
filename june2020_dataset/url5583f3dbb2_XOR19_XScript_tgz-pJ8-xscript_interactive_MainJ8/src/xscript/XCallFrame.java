package xscript;

import java.util.List;

import xscript.object.XConstPool;
import xscript.object.XObjectDataFunc;
import xscript.object.XObjectDataModule;
import xscript.object.XRuntime;
import xscript.values.XValue;

public class XCallFrame {

	private final XCallFrame parent;
	
	int line = -1;
	
	private final XValue function;
	
	private final XObjectDataFunc func;
	
	private final XConstPool constPool;
	
	private final XClosure[] closures;
	
	private final byte[] instructions;
	
	public int instructionpointer;
	
	final int basepointer;
	
	private boolean visibleToStacktrace;
	
	private XCatchHandler catchHandler;
	
	public XCallFrame(XRuntime rt, XCallFrame parent, XValue function, int basepointer, boolean visibleToStacktrace) {
		this.parent = parent;
		this.function = function;
		this.basepointer = basepointer;
		func = XUtils.getDataAs(rt, function, XObjectDataFunc.class);
		constPool = func.getConstPool(rt);
		instructions = func.getInstructions(rt);
		this.closures = func.getClosures();
		this.visibleToStacktrace = visibleToStacktrace;
	}

	public int readByte(){
		return instructions[instructionpointer++];
	}
	
	public int readUByte(){
		return instructions[instructionpointer++]&0xFF;
	}
	
	public int readShort(){
		return instructions[instructionpointer++]<<8 | (instructions[instructionpointer++]&0xFF);
	}
	
	public int readUShort(){
		return (instructions[instructionpointer++]&0xFF)<<8 | (instructions[instructionpointer++]&0xFF);
	}
	
	public int readUInt(){
		return (instructions[instructionpointer++]&0xFF)<<24 | (instructions[instructionpointer++]&0xFF)<<16 | (instructions[instructionpointer++]&0xFF)<<8 | (instructions[instructionpointer++]&0xFF);
	}

	public int readIntP(){
		int index = readUShort();
		return constPool.getIntP(index);
	}
	
	public long readLongP(){
		int index = readUShort();
		return constPool.getLongP(index);
	}
	
	public float readFloatP(){
		int index = readUShort();
		return constPool.getFloatP(index);
	}
	
	public double readDoubleP(){
		int index = readUShort();
		return constPool.getDoubleP(index);
	}
	
	public String readStringP(){
		int index = readUShort();
		return constPool.getStringP(index);
	}

	public XCallFrame getParent() {
		return parent;
	}

	public boolean isFinished() {
		return instructionpointer<0 || instructionpointer>=instructions.length;
	}

	public void jumpTo(int to) {
		instructionpointer = to;
	}

	public void exit() {
		instructionpointer = -1;
	}

	public void addToStackTrace(XRuntime rt, List<StackTraceElement> stackTrace) {
		if(visibleToStacktrace){
			String declaringClass = func.getFullPath(rt);
			String methodName = func.getName();
			String fileName = XUtils.getDataAs(rt, func.getModule(), XObjectDataModule.class).getName();
			stackTrace.add(new StackTraceElement(declaringClass, methodName, fileName, line));
		}
	}

	public XCatchHandler getCatchHandler() {
		if(catchHandler==null)
			return null;
		XCatchHandler ch = catchHandler;
		catchHandler = catchHandler.getNext();
		return ch;
	}

	public void addCatchHandler(int stackpointer, int instructionpointer) {
		catchHandler = new XCatchHandler(stackpointer, instructionpointer, catchHandler);
	}

	public void setVisible(XRuntime rt) {
		function.setVisible(rt);
		if(parent!=null){
			parent.setVisible(rt);
		}
	}
	
	public XClosure getClosure(int index){
		return closures[index];
	}
	
	public XValue getModule(){
		return func.getModule();
	}

	public XValue getMethodClass() {
		return func.getDeclaringClass();
	}
	
	public String getMethodName() {
		return func.getName();
	}

	public XValue getConstPool() {
		return func.getConstPool();
	}
	
}
