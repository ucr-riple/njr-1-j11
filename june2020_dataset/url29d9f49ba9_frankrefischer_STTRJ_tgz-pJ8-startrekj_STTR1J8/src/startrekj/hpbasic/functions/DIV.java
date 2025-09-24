package startrekj.hpbasic.functions;

import startrekj.hpbasic.CONST;
import startrekj.hpbasic.NumericExpression;

import static startrekj.hpbasic.Arithmetic.*;

public class DIV implements NumericExpression {
	private NumericExpression operand1;
	private NumericExpression operand2;
	private DIV(NumericExpression operand1, NumericExpression operand2) {
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	public static DIV DIV(NumericExpression operand1, NumericExpression operand2) {
		return new DIV(operand1, operand2);
	}
	public static DIV DIV(NumericExpression operand1, Number operand2) {
		return new DIV(operand1, new CONST(operand2));
	}
	public static DIV DIV(Number operand1, NumericExpression operand2) {
		return new DIV(new CONST(operand1), operand2);
	}
	public Number evaluate() {
		return div(operand1.evaluate(), operand2.evaluate());
	}
	@Override
	public String toString() {
		return "(" + operand1 + ")*(" + operand2 +")";
	}
}
