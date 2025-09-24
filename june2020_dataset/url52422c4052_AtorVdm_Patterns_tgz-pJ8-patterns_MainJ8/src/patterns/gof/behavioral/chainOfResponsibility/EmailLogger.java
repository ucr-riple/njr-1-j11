package patterns.gof.behavioral.chainOfResponsibility;

public class EmailLogger extends Logger {
	public EmailLogger(int mask) {
		super(mask);
	}

	@Override
	protected void writeMessage(String msg) {
		ChainOfResponsibilityClient.addOutput("e-mail log: " + msg);
	}
}