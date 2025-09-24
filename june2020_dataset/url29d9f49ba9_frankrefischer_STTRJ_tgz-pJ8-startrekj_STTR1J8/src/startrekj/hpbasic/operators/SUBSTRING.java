package startrekj.hpbasic.operators;

import startrekj.hpbasic.CONST;
import startrekj.hpbasic.NumericExpression;
import startrekj.hpbasic.StringExpression;

public class SUBSTRING implements StringExpression {
	private StringExpression expression;
	private NumericExpression from;
	private NumericExpression to;
	
	private SUBSTRING(StringExpression expression, NumericExpression from, NumericExpression to) {
		this.expression = expression;
		this.from = from;
		this.to = to;
	}
	
	public static SUBSTRING SUBSTRING(StringExpression expression, Number from, Number to) {
		return new SUBSTRING(expression, new CONST(from), new CONST(to));
	}
	public static SUBSTRING SUBSTRING(StringExpression expression, NumericExpression from, NumericExpression to) {
		return new SUBSTRING(expression, from, to);
	}
	
	@Override
	public String toString() {
		return expression + "[" + from + "," + to + "]";
	}

	public String evaluate() {
		String value = expression.evaluate();
		int beginIndex = from.evaluate().intValue()-1;
		int endIndex = to.evaluate().intValue();
		return value.substring(beginIndex, endIndex);
	}
}
