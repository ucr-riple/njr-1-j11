package startrekj.hpbasic.statements;

import startrekj.hpbasic.Statement;
import startrekj.hpbasic.StringExpression;
import startrekj.hpbasic.StringVariable;

public class SET_StringVariable implements Statement {
	private StringVariable variable;
	private int offset = 1;
	private Object value;
	
	private SET_StringVariable(StringVariable variable, int offset, Object value) {
		this.variable = variable;
		this.offset = offset;
		this.value = value;
	}
	
	public static SET_StringVariable SET(StringVariable variable, Object value) {
		return SET(variable, 1, value);
	}
	public static SET_StringVariable SET(StringVariable variable, int offset, Object value) {
		return new SET_StringVariable(variable, offset, value);
	}

	public void execute() {
		if(offset== 1)
			variable.setValue(getValueAsString());
		else
			variable.insert(offset, getValueAsString());
	}
	
	@Override
	public String toString() {
		if (offset == 1)
			return variable.getName() + "=\"" + value + "\"";
		return variable.getName() + "[" + offset + "]=\"" + value + "\"";
	}
	
	private String getValueAsString() {
		if (value instanceof StringExpression) {
			StringExpression expression = (StringExpression)value;
			return expression.evaluate();
		}
		return value.toString();
	}
}
