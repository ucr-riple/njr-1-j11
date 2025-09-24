package startrekj.hpbasic.statements;

import startrekj.hpbasic.HPBasicProgram;
import startrekj.hpbasic.CONST;
import startrekj.hpbasic.NumericExpression;
import startrekj.hpbasic.NumericVariable;
import startrekj.hpbasic.Statement;

public class FOR implements Statement {
	private NumericVariable variable;
	private NumericExpression from;
	private NumericExpression to;
	
	private FOR(NumericVariable variable) {
		this.variable = variable;
	}
	
	public static FOR FOR(NumericVariable variable) {
		return new FOR(variable);
	}
	public FOR FROM(Number number) {
		return FROM(new CONST(number));
	}
	public FOR FROM(NumericExpression from) {
		this.from = from;
		return this;
	}
	public FOR TO(Number number) {
		return TO(new CONST(number));
	}
	public FOR TO(NumericExpression to) {
		this.to = to;
		return this;
	}
	public String toString() {
		return "FOR " + variable.getName() + "=" + from + " TO " + to;
	}

	public void execute() {
		String varName = variable.getName();
		int lineNumber = HPBasicProgram.nextLineNumber;
		HPBasicProgram.lineNumberForLoopVariable.put(varName, lineNumber);
		HPBasicProgram.maxValueForLoopVariable.put(varName, to.evaluate().intValue());
		
		variable.setValue(from.evaluate().intValue());
	};
}
