package startrekj.hpbasic.statements;

import startrekj.hpbasic.CONST;
import startrekj.hpbasic.HPBasicProgram;
import startrekj.hpbasic.NumericExpression;
import startrekj.hpbasic.Statement;

public class GOTO implements Statement {
	private NumericExpression expression;
	private int[] lineNumbers;
	
	private GOTO(int lineNumber) {
		this(new CONST(1), lineNumber);
	}
	private GOTO(NumericExpression expression, int...lineNumbers) {
		this.expression = expression;
		this.lineNumbers = lineNumbers;
	}
	
	public static GOTO GOTO(int lineNumber) {
		return new GOTO(lineNumber);
	}
	
	public static GOTO GOTO(NumericExpression expression) {
		return new GOTO(expression);
	}
	
	public GOTO OF(int...lineNumbers) {
		this.lineNumbers = lineNumbers;
		return this;
	}
	
	public void execute() {
		int index = expression.evaluate().intValue()-1;
		if(index < 0)
			return;
		if(index >= lineNumbers.length)
			return;
		HPBasicProgram.nextLineNumber = lineNumbers[index];
	}
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		if(lineNumbers.length == 1)
			out.append("GOTO ").append(lineNumbers[0]);
		else {
			out.append("GOTO ").append(expression).append(" OF ");
			out.append(lineNumbers[0]);
			for(int lineNumber: lineNumbers)
				out.append(",").append(lineNumber);
		}
		return out.toString();
	}
}
