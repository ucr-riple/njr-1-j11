package startrekj.hpbasic.functions;

import startrekj.hpbasic.NumericExpression;

public class INT implements NumericExpression {
	private NumericExpression expression;
	private INT(NumericExpression expression) {
		this.expression = expression;
	}
	public static INT INT(NumericExpression expression) {
		return new INT(expression);
	}
	public Number evaluate() {
		return expression.evaluate().intValue();
	}
	@Override
	public String toString() {
		return "INT(" + expression + ")";
	}
}
