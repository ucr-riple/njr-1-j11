package startrekj.hpbasic.statements;

import startrekj.hpbasic.HPBasicProgram;
import startrekj.hpbasic.Statement;

public class TRACE implements Statement {
	private boolean traceOn;
	
	private TRACE(boolean traceOn) {
		this.traceOn = traceOn;
	}
	public static TRACE TRACE(boolean traceOn) {
		return new TRACE(traceOn);
	}
	public void execute() {
		HPBasicProgram.isTraceOn = traceOn;
	}
}
