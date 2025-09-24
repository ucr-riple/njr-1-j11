package startrekj.hpbasic.operators;

import startrekj.hpbasic.BooleanExpression;

public class OR implements BooleanExpression {
	private BooleanExpression expression1;
	private BooleanExpression expression2;
	
	private OR(BooleanExpression expression1, BooleanExpression expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	public static OR OR(BooleanExpression expression1, BooleanExpression expression2) {
		return new OR(expression1, expression2);
	}
	public boolean evaluate() {
		return expression1.evaluate() || expression2.evaluate();
	}
	@Override
	public String toString() {
		return expression1 + " OR " + expression2;
	}
}
