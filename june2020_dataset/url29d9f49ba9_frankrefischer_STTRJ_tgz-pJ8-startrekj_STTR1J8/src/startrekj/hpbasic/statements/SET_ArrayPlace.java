package startrekj.hpbasic.statements;

import startrekj.hpbasic.ArrayPlace;
import startrekj.hpbasic.CONST;
import startrekj.hpbasic.NumericExpression;
import startrekj.hpbasic.Statement;

public class SET_ArrayPlace implements Statement {
	private ArrayPlace[] arrayPlaces;
	private NumericExpression expression;
	
	private SET_ArrayPlace(ArrayPlace...arrayPlaces) {
		this.arrayPlaces = arrayPlaces;
	}
	
	public static SET_ArrayPlace SET(ArrayPlace...arrayPlaces) {
		return new SET_ArrayPlace(arrayPlaces);
	}
	public SET_ArrayPlace TO(int value) {
		return TO(new CONST(value));
	}
	public SET_ArrayPlace TO(NumericExpression expression) {
		this.expression = expression;
		return this;
	}
	public void execute() {
		Number value = expression.evaluate();
		for (ArrayPlace arrayPlace : arrayPlaces)
			arrayPlace.setValue(value);
	}
	public String toString() {
		StringBuilder out = new StringBuilder();
		for (ArrayPlace arrayPlace : arrayPlaces)
			out.append(arrayPlace).append("=");
		out.append(expression);
		return out.toString();
	};
}
