package startrekj.hpbasic.statements;

import startrekj.hpbasic.Statement;

public class REM implements Statement {
	private String text;
	
	private REM(String text) {
		this.text = text;
	}
	
	public static REM REM() {
		return REM("");
	}
	public static REM REM(String text) {
		return new REM(text);
	}
	
	@Override
	public String toString() {
		if ("".equals(text))
			return "REM";
		return "REM " + text;
	}

	public void execute() {
	}
}
