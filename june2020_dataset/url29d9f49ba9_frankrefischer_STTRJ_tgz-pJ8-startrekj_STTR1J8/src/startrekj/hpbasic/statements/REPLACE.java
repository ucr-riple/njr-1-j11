package startrekj.hpbasic.statements;

import startrekj.hpbasic.NumericExpression;
import startrekj.hpbasic.Statement;
import startrekj.hpbasic.StringExpression;
import startrekj.hpbasic.StringVariable;

public class REPLACE implements Statement {
	private StringVariable variable;
	private NumericExpression from;
	private NumericExpression to;
	private StringExpression expression;
	
	private REPLACE(StringVariable variable, NumericExpression from, NumericExpression to) {
		this.variable = variable;
		this.from = from;
		this.to = to;
	}
	public static REPLACE REPLACE(StringVariable variable, NumericExpression from, NumericExpression to) {
		return new REPLACE(variable, from, to);
	}
	public REPLACE WITH(StringExpression expression) {
		this.expression = expression;
		return this;
	}
	@Override
	public String toString() {
		return variable.getName() + "[" + from +"," + to + "]=" + expression;
	}
	public void execute() {
		variable.replace(from.evaluate().intValue(), to.evaluate().intValue(), expression.evaluate());
	}
}
