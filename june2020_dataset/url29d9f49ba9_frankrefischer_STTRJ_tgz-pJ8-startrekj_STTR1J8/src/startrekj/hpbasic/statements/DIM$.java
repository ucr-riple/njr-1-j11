package startrekj.hpbasic.statements;

import startrekj.hpbasic.Statement;
import startrekj.hpbasic.StringVariable;

public class DIM$ implements Statement {

	private StringVariable variable;
	private int size;

	public DIM$(StringVariable variable, int size) {
		this.variable = variable;
		this.size = size;
	}

	public static DIM$ DIM(StringVariable variable, int size) {
		return new DIM$(variable, size);
	}

	public void execute() {
		variable.initialize(size);
	}
	@Override
	public String toString() {
		return "DIM " + variable.getName() + "[" + size + "]";
	}
}
