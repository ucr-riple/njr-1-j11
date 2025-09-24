package startrekj.hpbasic;

public class FunctionVariable implements NumericExpression {
	private NumericExpression body;
	private String name;

	public FunctionVariable(String name) {
		this.name = name;
	}
	
	public void setBody(NumericExpression body) {
		this.body = body;
	}

	public Number evaluate() {
		return body.evaluate();
	}

	public String getName() {
		return name;
	}
}
