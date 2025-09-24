package startrekj.hpbasic.operators;

import startrekj.hpbasic.BooleanExpression;
import startrekj.hpbasic.CONST;
import startrekj.hpbasic.NumericExpression;

import static startrekj.hpbasic.Arithmetic.*;

public class LTE implements BooleanExpression {
	private NumericExpression expression1;
	private NumericExpression expression2;
	
	private LTE(NumericExpression expression1, NumericExpression expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	public static LTE LTE(NumericExpression expression1, Number number) {
		return LTE(expression1, new CONST(number));
	}
	public static LTE LTE(NumericExpression expression1, NumericExpression expression2) {
		return new LTE(expression1, expression2);
	}
	public boolean evaluate() {
		return lessThanOrEqual(expression1.evaluate(), expression2.evaluate());
	}
	@Override
	public String toString() {
		return expression1 + "<=" + expression2;
	}
}
