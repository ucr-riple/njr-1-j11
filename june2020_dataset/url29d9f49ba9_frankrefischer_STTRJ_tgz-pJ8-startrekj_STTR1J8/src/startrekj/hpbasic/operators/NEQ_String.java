package startrekj.hpbasic.operators;

import startrekj.hpbasic.BooleanExpression;
import startrekj.hpbasic.CONST_String;
import startrekj.hpbasic.StringExpression;
import startrekj.hpbasic.StringVariable;

public class NEQ_String implements BooleanExpression {
	private StringExpression expression1;
	private StringExpression expression2;
	
	private NEQ_String(StringExpression expression1, StringExpression expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	public static NEQ_String NEQ(StringExpression expression1, String string) {
		return new NEQ_String(expression1, new CONST_String(string));
	}
	public static NEQ_String NEQ(StringExpression expression1, StringExpression expression2) {
		return new NEQ_String(expression1, expression2);
	}
	public boolean evaluate() {
		String value1 = expression1.evaluate();
		String value2 = expression2.evaluate();
		return !value1.equals(value2);
	}
	@Override
	public String toString() {
		return expression1 + " <> " +expression2;
	}
}
