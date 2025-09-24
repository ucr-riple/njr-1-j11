package test.java;

import main.java.TestExistsException;
import main.java.TestSuite;
import main.java.TestRunner;

public class FrameworkTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestRunner testRunner = new TestRunner();
		TestSuite testSuiteTest = new TestSuite("Test suite for testing Test");
		TestSuite testSuiteAffirm = new TestSuite("Test suite for testing Affirm");
		TestSuite testSuiteAll = new TestSuite("Test all");
		
		EqualsObjectTest equalsObjectTest = new EqualsObjectTest("Test two objects are equal");
		NotEqualsObjectTest notEqualsObjectTest = new NotEqualsObjectTest("Test two objects are different");
		NotEqualsIntTest notEqualsIntTest = new NotEqualsIntTest("Test two integers are different");
		EqualsIntTest equalsIntTest = new EqualsIntTest("Test two integers are equal");
		NotEqualsFloatTest notEqualsFloatTest = new NotEqualsFloatTest("Test two floats are different");
		EqualsFloatTest equalsFloatTest = new EqualsFloatTest("Test two floats are equal");
		NotEqualsBooleanTest notEqualsBooleanTest = new NotEqualsBooleanTest("Test two booleans are different");
		EqualsBooleanTest equalsBooleanTest = new EqualsBooleanTest("Test two booleans are equal");
		NotEqualsCharTest notEqualsCharTest = new NotEqualsCharTest("Test two chars are different");
		EqualsCharTest equalsCharTest = new EqualsCharTest("Test two chars are equal");
		
		TestEqualsObjectTest testEqualsObjectTest = new TestEqualsObjectTest("Test two objects are equal using the Test class' methods");
		TestNotEqualsObjectTest testNotEqualsObjectTest = new TestNotEqualsObjectTest("Test two objects are different using the Test class' methods");
		TestNotEqualsIntTest testNotEqualsIntTest = new TestNotEqualsIntTest("Test two integers are different using the Test class' methods");
		TestEqualsIntTest testEqualsIntTest = new TestEqualsIntTest("Test two integers are equal using the Test class' methods");
		TestNotEqualsFloatTest testNotEqualsFloatTest = new TestNotEqualsFloatTest("Test two floats are different using the Test class' methods");
		TestEqualsFloatTest testEqualsFloatTest = new TestEqualsFloatTest("Test two floats are equal using the Test class' methods");
		TestNotEqualsBooleanTest testNotEqualsBooleanTest = new TestNotEqualsBooleanTest("Test two booleans are different using the Test class' methods");
		TestEqualsBooleanTest testEqualsBooleanTest = new TestEqualsBooleanTest("Test two booleans are equal using the Test class' methods");
		TestNotEqualsCharTest testNotEqualsCharTest = new TestNotEqualsCharTest("Test two chars are different using the Test class' methods");
		TestEqualsCharTest testEqualsCharTest = new TestEqualsCharTest("Test two chars are equal using the Test class' methods");
		
		try {
			testSuiteAffirm.addTest(equalsObjectTest);
			testSuiteAffirm.addTest(notEqualsObjectTest);
			testSuiteAffirm.addTest(notEqualsIntTest);
			testSuiteAffirm.addTest(equalsIntTest);
			testSuiteAffirm.addTest(notEqualsFloatTest);
			testSuiteAffirm.addTest(equalsFloatTest);
			testSuiteAffirm.addTest(notEqualsBooleanTest);
			testSuiteAffirm.addTest(equalsBooleanTest);
			testSuiteAffirm.addTest(notEqualsCharTest);
			testSuiteAffirm.addTest(equalsCharTest);
			
			testSuiteTest.addTest(testEqualsObjectTest);
			testSuiteTest.addTest(testNotEqualsObjectTest);
			testSuiteTest.addTest(testNotEqualsIntTest);
			testSuiteTest.addTest(testEqualsIntTest);
			testSuiteTest.addTest(testNotEqualsFloatTest);
			testSuiteTest.addTest(testEqualsFloatTest);
			testSuiteTest.addTest(testNotEqualsBooleanTest);
			testSuiteTest.addTest(testEqualsBooleanTest);
			testSuiteTest.addTest(testNotEqualsCharTest);
			testSuiteTest.addTest(testEqualsCharTest);
			
			testSuiteAll.addTest(testSuiteAffirm);
			testSuiteAll.addTest(testSuiteTest);
			
		}catch (TestExistsException e) {
			
		}
		
		testRunner.startTesting(testSuiteAll);

	}
}
