package main.java;

public class TestResultError extends TestResult {

	public TestResultError(String testName) {
		super (testName);
	}
	
	public String print() {
		return "[error] " + this.testName;
	}

	@Override
	public void updateMe(ResultPrinter printer) {
		printer.oneMoreTestErrored();
	}
}
