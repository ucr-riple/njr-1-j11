package startrekj.hpbasic.statements;

import startrekj.hpbasic.CONST;
import startrekj.hpbasic.NumericExpression;
import startrekj.hpbasic.NumericVariable;
import startrekj.hpbasic.Statement;

public class SET_NumericVariable implements Statement, NumericExpression {
	private NumericVariable variable;
	private NumericExpression expression;
	
	private SET_NumericVariable(NumericVariable variable, NumericExpression expression) {
		this.variable = variable;
		this.expression = expression;
	}
	
	public static SET_NumericVariable SET(NumericVariable variable, int number) {
		return new SET_NumericVariable(variable, new CONST(number));
	}
	public static SET_NumericVariable SET(NumericVariable variable, NumericExpression expression) {
		return new SET_NumericVariable(variable, expression);
	}

	public void execute() {
		variable.setValue(expression.evaluate());
	}
	
	@Override
	public String toString() {
		return variable.getName() + "=" + expression + "";
	}

	public Number evaluate() {
		execute();
		return variable.getValue();
	}
}
