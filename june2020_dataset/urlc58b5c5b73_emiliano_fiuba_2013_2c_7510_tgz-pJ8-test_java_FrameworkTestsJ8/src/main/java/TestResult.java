package main.java;

/**
 * This class collects the result of a test and shows the result
 * using ResultBoard class.
 * 
 * @author Miguenz, Rodriguez, Suárez
 *
 */
public abstract class TestResult {

	protected String testName;
    
    public TestResult(String testName) {
    	this.testName = testName;
    }
    
    public String print() {
    	return "";
    }
    
    public abstract void updateMe(ResultPrinter printer);
}
