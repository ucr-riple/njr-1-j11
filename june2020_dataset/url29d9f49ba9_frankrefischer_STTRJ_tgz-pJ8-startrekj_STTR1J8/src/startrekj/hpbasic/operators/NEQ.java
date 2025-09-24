package startrekj.hpbasic.operators;

import startrekj.hpbasic.BooleanExpression;
import startrekj.hpbasic.CONST;
import startrekj.hpbasic.NumericExpression;

import static startrekj.hpbasic.Arithmetic.*;

public class NEQ implements BooleanExpression {
	private NumericExpression expression1;
	private NumericExpression expression2;
	
	private NEQ(NumericExpression expression1, NumericExpression expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	public static NEQ NEQ(NumericExpression expression1, Number number) {
		return new NEQ(expression1, new CONST(number));
	}
	public static NEQ NEQ(NumericExpression expression1, NumericExpression expression2) {
		return new NEQ(expression1, expression2);
	}
	public boolean evaluate() {
		return notEqual(expression1.evaluate(), expression2.evaluate());
	}
	@Override
	public String toString() {
		return expression1 + "<>" + expression2;
	}
}
