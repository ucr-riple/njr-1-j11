package startrekj.hpbasic.statements;

import startrekj.hpbasic.NumericExpression;
import startrekj.hpbasic.NumericVariable;
import startrekj.hpbasic.Statement;
import startrekj.hpbasic.StringExpression;

public class PRINT implements Statement {

	private Object[] parts;
	private PRINT(Object...parts) {
		this.parts = parts;
	}
	
	public static PRINT PRINT(Object...parts) {
		return new PRINT(parts);
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("PRINT ");
		for(Object part: parts) {
			if (part instanceof String)
				out.append("\"" + part + "\"");
			else
				out.append(part).append(";");
		}
		return out.toString();
	}

	public void execute() {
		for(Object part: parts)
			print(part);
		System.out.println();
		System.out.flush();
	}

	private void print(Object part) {
		if (part instanceof NumericExpression) {
			NumericExpression expression = (NumericExpression) part;
			System.out.print(" " + expression.evaluate());
		} else if (part instanceof StringExpression) {
			StringExpression expression = (StringExpression) part;
			System.out.println(" " + expression.evaluate());
		}
		else
			System.out.print(part);
	}

}
