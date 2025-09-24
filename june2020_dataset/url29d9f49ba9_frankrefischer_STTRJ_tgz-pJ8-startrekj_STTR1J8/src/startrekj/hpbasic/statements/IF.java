package startrekj.hpbasic.statements;

import startrekj.hpbasic.BooleanExpression;
import startrekj.hpbasic.HPBasicProgram;
import startrekj.hpbasic.Statement;

public class IF implements Statement {
	private BooleanExpression condition;
	private Integer lineNumber = null;
	
	private IF(BooleanExpression condition) {
		this.condition = condition;
	}
	
	public static IF IF(BooleanExpression condition) {
		return new IF(condition);
	}
	public IF THEN(int lineNumber) {
		this.lineNumber = lineNumber;
		return this;
	}
	@Override
	public String toString() {
		return "IF " + condition + " THEN " + lineNumber;
	}

	public void execute() {
		if(condition.evaluate()) {
			HPBasicProgram.nextLineNumber = lineNumber;
		}
	}
}
