package startrekj.hpbasic.operators;

import startrekj.hpbasic.BooleanExpression;
import startrekj.hpbasic.CONST_String;
import startrekj.hpbasic.StringExpression;
import startrekj.hpbasic.StringVariable;

public class EQ_String implements BooleanExpression {
	private StringExpression expression1;
	private StringExpression expression2;
	
	private EQ_String(StringExpression expression1, StringExpression expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	public static EQ_String EQ(StringExpression expression1, String string) {
		return new EQ_String(expression1, new CONST_String(string));
	}
	public static EQ_String EQ(StringExpression expression1, StringExpression expression2) {
		return new EQ_String(expression1, expression2);
	}
	public boolean evaluate() {
		String value1 = expression1.evaluate();
		String value2 = expression2.evaluate();
		return value1.equals(value2);
	}
	@Override
	public String toString() {
		return expression1 + " = " +expression2;
	}
}
