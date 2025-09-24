package main.java;

/**
 * This class provides the public interface which can be used for the
 * client's test.
 * Using the class TestResult, it informs the result of the test.
 * 
 * @author Miguenz, Rodriguez, Suárez
 *
 */
public abstract class Test {
	
	protected String name;
	protected TestResult result;
	protected Fixture fixture;
	
	public Test (String newName) {
		name = newName;
	}
	
	public abstract void runTest();
	public void setUp() { };
	public void tearDown() { };
	
	public String getName() {
		return name;
	}
    
	public void setName(String newName) {
		name = newName;
	}
	
	public void setResult(TestResult testResult) {
		result = testResult;
	}
	
	public TestResult getResult() {
		return result;
	}
	
	public void setFixture(Fixture fixture) {
		this.fixture = fixture;	
	}

	public Fixture getFixture() {
		return fixture;	
	}
	
	public boolean equals(Test test) {
		return this.name == test.getName();
	}
	
    /**
     * @param methodName test's method name
     * @param a int value to be compared with b
     * @param b int value to be compared with a
     */
    public void assertEquals(String methodName, int a, int b) {
        boolean result = Affirm.checkEquals(a, b);
        createTestResult(result);
    }
    
    /**
     * @param methodName test's method name
     * @param a int value to be compared with b
     * @param b int value to be compared with a
     */
    public void assertNotEquals(String methodName, int a, int b) {
        boolean result = Affirm.checkNotEquals(a, b);
        createTestResult(result);
    }
    
    /**
     * @param methodName test's method name
     * @param a char value to be compared with b
     * @param b char value to be compared with a
     */
    public void assertEquals(String methodName, char a, char b) {
        boolean result = Affirm.checkEquals(a, b);
        createTestResult(result);
    }
    
    /**
     * @param methodName test's method name
     * @param a char value to be compared with b
     * @param b char value to be compared with a
     */
    public void assertNotEquals(String methodName, char a, char b) {
        boolean result = Affirm.checkNotEquals(a, b);
        createTestResult(result);
    }
    
    /**
     * @param methodName test's method name
     * @param a boolean value to be compared with b
     * @param b boolean value to be compared with a
     */
    public void assertEquals(String methodName, boolean a, boolean b) {
        boolean result = Affirm.checkEquals(a, b);
        createTestResult(result);
    }
    
    /**
     * @param methodName test's method name
     * @param a boolean value to be compared with b
     * @param b boolean value to be compared with a
     */
    public void assertNotEquals(String methodName, boolean a, boolean b) {
        boolean result = Affirm.checkNotEquals(a, b);
        createTestResult(result);
    }
    
    /**
     * @param methodName test's method name
     * @param a Object to be compared with b
     * @param b Object to be compared with a
     */
    public void assertEquals(String methodName, Object a, Object b) {
        boolean result = Affirm.checkEquals(a, b);
        createTestResult(result);
    }
    
    /**
     * @param methodName test's method name
     * @param a Object to be compared with b
     * @param b Object to be compared with a
     */
    public void assertNotEquals(String methodName, Object a, Object b) {
        boolean result = Affirm.checkNotEquals(a, b);
        createTestResult(result);
    }
    
    /**
     * @param methodName test's method name
     * @param a float value to be compared with b
     * @param b float value to be compared with a
     */
    public void assertEquals(String methodName, float a, float b) {
        boolean result = Affirm.checkNotEquals(a, b);
        createTestResult(result);
    }
    
    /**
     * @param methodName test's method name
     * @param a float value to be compared with b
     * @param b float value to be compared with a
     */
    public void assertNotEquals(String methodName, float a, float b) {
        boolean result = Affirm.checkNotEquals(a, b);
        createTestResult(result);
    }

    /**
     * @param methodName test's method name
     * @param expression boolean expression to be tested for truth
     */
    public void assertTrue(String methodName, boolean expression) {
    	boolean result = Affirm.checkTrue(expression);
    	createTestResult(result);
    }
    
    public void failure() {
    	assertTrue("failure", false);
    }
    
    /**
     * @param r boolean expression used to decide which TestResult to create
     */
    private void createTestResult(boolean r) {

        if (r == false) {
       	 this.result = new TestResultFail(this.getName());
       } 
       else {
       	this.result = new TestResultOk(this.getName());
       }
    }
}
