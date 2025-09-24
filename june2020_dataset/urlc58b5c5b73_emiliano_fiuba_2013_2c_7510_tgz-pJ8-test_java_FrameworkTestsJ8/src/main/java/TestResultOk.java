package main.java;

public class TestResultOk extends TestResult {
	
	public TestResultOk(String testName) {
		super (testName);
	}
	
	public String print() {
		return "[ok] " + this.testName;
	}

	@Override
	public void updateMe(ResultPrinter printer) {
		printer.oneMoreTestOk();
	}
}
