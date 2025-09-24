package patterns.gof.behavioral.chainOfResponsibility;

public class FileLogger extends Logger {
	public FileLogger(int mask) {
		super(mask);
	}

	@Override
	protected void writeMessage(String msg) {
		ChainOfResponsibilityClient.addOutput("file log: " + msg);
	}
}