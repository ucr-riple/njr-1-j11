package startrekj.hpbasic.functions;

import startrekj.hpbasic.NumericExpression;

public class SQR implements NumericExpression {
	private NumericExpression operand;
	
	private SQR(NumericExpression operand) {
		this.operand = operand;
	}
	
	public static SQR SQR(NumericExpression operand) {
		return new SQR(operand);
	}
	@Override
	public String toString() {
		return "SQR(" + operand + ")";
	
	}
	public Number evaluate() {	
		return Math.sqrt(operand.evaluate().doubleValue());
	}
}
