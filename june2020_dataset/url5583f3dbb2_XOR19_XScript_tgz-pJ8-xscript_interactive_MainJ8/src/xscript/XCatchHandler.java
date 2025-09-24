package xscript;

class XCatchHandler {

	private XCatchHandler next;
	
	private int instructionpointer;
	
	private int stackpointer;

	public XCatchHandler(int stackpointer, int instructionpointer, XCatchHandler next) {
		this.next = next;
		this.instructionpointer = instructionpointer;
		this.stackpointer = stackpointer;
	}

	public int getStackPointer() {
		return stackpointer;
	}

	public int getInstructionPointer() {
		return instructionpointer;
	}

	public XCatchHandler getNext() {
		return next;
	}
	
}
