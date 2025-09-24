package main.java;

import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Pattern;
import main.java.TestExistsException;

public class TestSuite extends Test {

	private HashMap<String,Test> tests;

	public TestSuite (String newName) {
		super(newName);
		tests = new HashMap<String, Test>();
	}
	
	@Override
	public void runTest() {
		ResultPrinter.getInstance().addSuite(this.getName());
		setUp();
		
		Collection<Test> col = tests.values();		

		for (Test test : col) {
			test.setUp();
			try {
				test.runTest();
			} catch (Exception e) {
				test.setResult(new TestResultError(test.getName()));
			}
			test.tearDown();
			
			ResultPrinter.getInstance().addTestResults(test);
		}

		tearDown();
		ResultPrinter.getInstance().removeSuite(this.getName());
	}

	public void runTest(String pattern) {
		setUp();
		
		Collection<Test> col = tests.values();

		for (Test test : col) {
			if (Pattern.matches(pattern, test.getName())) {
				test.setUp();
				test.runTest();
				test.tearDown();
			}
		}

		tearDown();
	}
	
	public void addTest(Test test) throws TestExistsException {
		if (!tests.containsKey(test.getName())) {
			tests.put(test.getName(), test);
		}
		else { 
			throw new TestExistsException(test.getName() 
					+ " already present in TestSuite "
					+ this.getName()); 
		}
	}

}
