package patterns.gof.behavioral.chainOfResponsibility;

public class ConsoleLogger extends Logger {	
	public ConsoleLogger(int mask) {
		super(mask);
	}

	@Override
	protected void writeMessage(String msg) {
		ChainOfResponsibilityClient.addOutput("console log: " + msg);
	}
}