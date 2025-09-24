package startrekj.hpbasic.statements;

import startrekj.hpbasic.HPBasicProgram;
import startrekj.hpbasic.Statement;

public class GOSUB implements Statement {
	
	int lineNumber;

	private GOSUB(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public static GOSUB GOSUB(int lineNumber) {
		return new GOSUB(lineNumber);
	}
	
	@Override
	public String toString() {
		return "GOSUB " + lineNumber;
	}

	public void execute() {
		RETURN.lineNumberStack.push(HPBasicProgram.nextLineNumber);
		HPBasicProgram.nextLineNumber = lineNumber;
	}
}
