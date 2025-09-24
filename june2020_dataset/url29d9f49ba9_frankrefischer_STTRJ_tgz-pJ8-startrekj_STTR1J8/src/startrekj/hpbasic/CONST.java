package startrekj.hpbasic;


public class CONST implements NumericExpression {
	private Number number;
	public CONST(Number number) {
		this.number = number;
	}
	public Number evaluate() {
		return number;
	}
	@Override
	public String toString() {
		return number.toString();
	}
}
