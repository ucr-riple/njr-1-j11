package startrekj.hpbasic.functions;

import startrekj.hpbasic.CONST;
import startrekj.hpbasic.NumericExpression;

import static startrekj.hpbasic.Arithmetic.*;

public class MULT implements NumericExpression {
	private NumericExpression operand1;
	private NumericExpression operand2;
	private MULT(NumericExpression operand1, NumericExpression operand2) {
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	public static MULT MULT(NumericExpression operand1, NumericExpression operand2) {
		return new MULT(operand1, operand2);
	}
	public static MULT MULT(NumericExpression operand1, Number operand2) {
		return new MULT(operand1, new CONST(operand2));
	}
	public static MULT MULT(Number operand1, NumericExpression operand2) {
		return new MULT(new CONST(operand1), operand2);
	}
	public Number evaluate() {
		return mult(operand1.evaluate(), operand2.evaluate());
	}
	@Override
	public String toString() {
		return "(" + operand1 + ")*(" + operand2 +")";
	}
}
