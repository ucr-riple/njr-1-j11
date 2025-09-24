package startrekj.hpbasic.statements;

import startrekj.hpbasic.Statement;

public class EXIT implements Statement {
	private EXIT() {
	}
	
	public static EXIT EXIT() {
		return new EXIT();
	}

	public void execute() {
		throw new ExitException();
	}
	
	@Override
	public String toString() {
		return "EXIT";
	}
}
