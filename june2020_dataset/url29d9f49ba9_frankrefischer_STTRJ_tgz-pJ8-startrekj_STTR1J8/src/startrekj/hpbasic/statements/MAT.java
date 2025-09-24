package startrekj.hpbasic.statements;

import startrekj.hpbasic.ArrayVariable;
import startrekj.hpbasic.Statement;

public class MAT implements Statement {
	private ArrayVariable variable;
	private Number value;
	
	private MAT(ArrayVariable variable, Number value) {
		this.variable = variable;
		this.value = value;
	}
	
	public static MAT MAT(ArrayVariable variable, Number value) {
		return new MAT(variable, value);
	}

	public void execute() {
		variable.setAllTo(value);
	}
	
	public String toString() {
		return "MAT " + variable.getName() + "=" + value;
	};
}
