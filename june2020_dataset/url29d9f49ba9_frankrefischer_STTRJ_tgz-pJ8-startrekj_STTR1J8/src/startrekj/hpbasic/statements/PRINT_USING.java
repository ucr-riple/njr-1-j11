package startrekj.hpbasic.statements;

import startrekj.hpbasic.HPBasicProgram;
import startrekj.hpbasic.NumericExpression;
import startrekj.hpbasic.Statement;
import startrekj.hpbasic.StringExpression;

public class PRINT_USING implements Statement {

	private int lineNumber;
	private Object[] parameters;
	
	private PRINT_USING(int lineNumber, Object...parameters) {
		this.lineNumber = lineNumber;
		this.parameters = parameters;
	}
	
	public static PRINT_USING PRINT_USING(int lineNumber, Object...parameters) {
		return new PRINT_USING(lineNumber, parameters);
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("PRINT USING ").append(lineNumber);
		for(Object parameter: parameters)
			out.append(";").append(parameter);
		return out.toString();
	}

	public void execute() {
		String formatString = HPBasicProgram.getFormatString(lineNumber);
		System.out.printf(formatString, getEvaluatedParameters());
		System.out.println();
	}

	private Object[] getEvaluatedParameters() {
		Object[] evaluatedParameters = new Object[parameters.length];
		for(int i = 0; i < parameters.length; ++i)
			evaluatedParameters[i] = getEvaluatedParameter(i);
		return evaluatedParameters;
	}

	private Object getEvaluatedParameter(int i) {
		Object parameter = parameters[i];
		if (parameter instanceof StringExpression) {
			StringExpression expression = (StringExpression) parameter;
			return expression.evaluate();
		}
		if (parameter instanceof NumericExpression) {
			NumericExpression expression = (NumericExpression) parameter;
			return expression.evaluate();
		}
		return parameter;
	}
}
