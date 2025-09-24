package startrekj.hpbasic.statements;

import startrekj.hpbasic.HPBasicProgram;
import startrekj.hpbasic.NumericVariable;
import startrekj.hpbasic.Statement;

import static startrekj.hpbasic.Arithmetic.*;

public class NEXT implements Statement {

	private NumericVariable variable;

	private NEXT(NumericVariable variable) {
		this.variable = variable;
	}

	public static NEXT NEXT(NumericVariable variable) {
		return new NEXT(variable);
	}
	
	@Override
	public String toString() {
		return "NEXT " + variable.getName();
	}

	public void execute() {
		String varName = variable.getName();

		variable.incrementByOne();

		int maximalValue = HPBasicProgram.maxValueForLoopVariable.get(varName);
		
		if(lessThan(variable.getValue(), maximalValue+1)) {
			HPBasicProgram.nextLineNumber = HPBasicProgram.lineNumberForLoopVariable.get(varName);
		} else {
			HPBasicProgram.maxValueForLoopVariable.remove(variable.getName());
			HPBasicProgram.lineNumberForLoopVariable.remove(varName);
		}
	}
}
