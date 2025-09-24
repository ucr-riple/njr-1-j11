package startrekj.hpbasic.functions;

import startrekj.hpbasic.NumericExpression;

import static startrekj.hpbasic.Arithmetic.*;

public class ABS implements NumericExpression {
	private NumericExpression expression;
	private ABS(NumericExpression expression) {
		this.expression = expression;
	}
	public static ABS ABS(NumericExpression expression) {
		return new ABS(expression);
	}
	public Number evaluate() {
		return abs(expression.evaluate());
	}
	@Override
	public String toString() {
		return "ABS(" + expression + ")";
	}
}
