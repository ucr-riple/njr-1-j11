package startrekj.hpbasic.functions;

import startrekj.hpbasic.CONST;
import startrekj.hpbasic.NumericExpression;

import static startrekj.hpbasic.Arithmetic.*;

public class ADD implements NumericExpression {
	private NumericExpression operand1;
	private NumericExpression operand2;
	private ADD(NumericExpression operand1, NumericExpression operand2) {
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	public static ADD ADD(NumericExpression operand1, NumericExpression operand2) {
		return new ADD(operand1, operand2);
	}
	public static ADD ADD(NumericExpression operand1, Number operand2) {
		return new ADD(operand1, new CONST(operand2));
	}
	public Number evaluate() {
		return add(operand1.evaluate(), operand2.evaluate());
	}
	@Override
	public String toString() {
		return operand1 + "+" + operand2;
	}
}
