package startrekj.hpbasic;

public class ArrayPlace implements NumericExpression {
	private ArrayVariable variable;
	private NumericExpression i;
	private NumericExpression j;
	
	public ArrayPlace(ArrayVariable variable, NumericExpression i, NumericExpression j) {
		this.variable = variable;
		this.i = i;
		this.j = j;
	}

	public void setValue(Number value) {
		variable.setValue(i.evaluate().intValue(), j.evaluate().intValue(), value);
	}

	public Number getValue() {
		return variable.get(i.evaluate().intValue(), j.evaluate().intValue());
	}
	@Override
	public String toString() {
		return variable.getName() + "[" + i + "," + j + "]";
	}

	public Number evaluate() {
		return getValue();
	}
}
