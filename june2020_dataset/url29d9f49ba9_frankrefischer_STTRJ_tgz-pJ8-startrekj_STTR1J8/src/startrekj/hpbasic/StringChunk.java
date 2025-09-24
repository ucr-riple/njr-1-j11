package startrekj.hpbasic;

public class StringChunk implements StringExpression {
	private StringExpression expression;
	private NumericExpression from;
	private NumericExpression to;
	
	public StringChunk(StringExpression expression, NumericExpression from, NumericExpression to) {
		this.expression = expression;
		this.from = from;
		this.to = to;
	}

	public String evaluate() {
		int beginIndex = from.evaluate().intValue()-1;
		int endIndex = to.evaluate().intValue();
		String string = expression.evaluate();
		if(endIndex > string.length())
			endIndex = string.length();
		return string.substring(beginIndex, endIndex);
	}
	@Override
	public String toString() {
		return expression + "[" + from + "," + to + "]";
	}
}
