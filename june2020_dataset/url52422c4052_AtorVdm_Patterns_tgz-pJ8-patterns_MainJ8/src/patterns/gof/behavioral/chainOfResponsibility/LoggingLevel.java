package patterns.gof.behavioral.chainOfResponsibility;

public enum LoggingLevel {
	None(0),                    //        0
    Info(1),                    //        1
    Debug(2),                   //       10
    Warning(4),                 //      100
    Error(8),                   //     1000
    FunctionalMessage(16),		//    10000
    FunctionalError(32),        //   100000
    All(63);                    //   111111
	
	private int code;
	
	private LoggingLevel(int c) {
		code = c;
	}
	 
	public int getCode() {
		return code;
	}
}