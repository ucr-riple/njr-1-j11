package main.java;

public class TestRunner {  
	
	public void startTesting(TestSuite testSuite) {
		testSuite.runTest();
		ResultPrinter.getInstance().print();
	}
	
	public void startTesting(TestSuite testSuite, String pattern) {
		testSuite.runTest(pattern);
	}
}
