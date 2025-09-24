package main.java;

public class TestResultFail extends TestResult {
	
	public TestResultFail(String testName) {
		super (testName);
	}
	
	public String print() {
		return "[fail] " + this.testName;
	}

	@Override
	public void updateMe(ResultPrinter printer) {
		printer.oneMoreTestFailed();
	}
}
