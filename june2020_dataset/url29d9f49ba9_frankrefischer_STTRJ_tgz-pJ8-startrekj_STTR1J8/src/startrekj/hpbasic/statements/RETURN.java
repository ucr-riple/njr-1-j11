package startrekj.hpbasic.statements;

import java.util.Stack;

import startrekj.hpbasic.HPBasicProgram;
import startrekj.hpbasic.Statement;

public class RETURN implements Statement {

	public static Stack<Integer> lineNumberStack = new Stack<Integer>();

	private RETURN() {
	}
	
	public static RETURN RETURN() {
		return new RETURN();
	}
	
	@Override
	public String toString() {
		return "RETURN";
	}

	public void execute() {
		HPBasicProgram.nextLineNumber = lineNumberStack.pop();
	}
}
