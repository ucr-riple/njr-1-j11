package startrekj.hpbasic.statements;

import startrekj.hpbasic.FunctionVariable;
import startrekj.hpbasic.NumericExpression;
import startrekj.hpbasic.Statement;

public class DEF implements Statement {
	private FunctionVariable variable;
	private NumericExpression body;
	
	private DEF(FunctionVariable variable, NumericExpression body) {
		this.variable = variable;
		this.body = body;
	}
	
	public static DEF DEF(FunctionVariable variable, NumericExpression body) {
		return new DEF(variable, body);
	}
	public void execute() {
		variable.setBody(body);
	}
	@Override
	public String toString() {
		return "DEF " + variable.getName() + "()=" + body.toString();
	}
}
