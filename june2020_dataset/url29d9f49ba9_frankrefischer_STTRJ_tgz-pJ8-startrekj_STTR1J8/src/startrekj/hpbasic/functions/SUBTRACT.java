package startrekj.hpbasic.functions;

import startrekj.hpbasic.CONST;
import startrekj.hpbasic.NumericExpression;

import static startrekj.hpbasic.Arithmetic.*;

public class SUBTRACT implements NumericExpression {
	private NumericExpression operand1;
	private NumericExpression operand2;
	private SUBTRACT(NumericExpression operand1, NumericExpression operand2) {
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	public static SUBTRACT SUBTRACT(NumericExpression operand1, NumericExpression operand2) {
		return new SUBTRACT(operand1, operand2);
	}
	public static SUBTRACT SUBTRACT(NumericExpression operand1, Number operand2) {
		return new SUBTRACT(operand1, new CONST(operand2));
	}
	public Number evaluate() {
		return subtract(operand1.evaluate(), operand2.evaluate());
	}
	@Override
	public String toString() {
		return operand1 + "-(" + operand2 + ")";
	}
}
