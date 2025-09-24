package startrekj.hpbasic.statements;

import startrekj.hpbasic.ArrayVariable;
import startrekj.hpbasic.Statement;

public class DIM implements Statement {

	private ArrayVariable variable;
	private int size1;
	private int size2;

	public DIM(ArrayVariable variable, int size1, int size2) {
		this.variable = variable;
		this.size1 = size1;
		this.size2 = size2;
	}

	public static DIM DIM(ArrayVariable variable, int size1, int size2) {
		return new DIM(variable, size1, size2);
	}
	public static DIM DIM(ArrayVariable variable, int size) {
		return DIM(variable, size, 1);
	}

	public void execute() {
		variable.setSize(size1, size2);
	}
	@Override
	public String toString() {
		if(size2>1)
			return "DIM " + variable.getName() + "[" + size1 + "," + size2 + "]";
		return "DIM " + variable.getName() + "[" + size1 + "]";
	}
}
