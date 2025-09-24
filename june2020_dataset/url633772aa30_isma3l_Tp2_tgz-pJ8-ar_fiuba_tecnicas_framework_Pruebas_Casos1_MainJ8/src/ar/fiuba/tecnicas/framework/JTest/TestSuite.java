/*

 */

package ar.fiuba.tecnicas.framework.JTest;

import java.util.HashMap;
import java.util.Vector;
/*
Responsabilidad: almacenar test y testsuite en una estructura (es el la clase "composite")
* */
public class TestSuite implements Test {
    private Vector<Test> testlineitem;
    private boolean firsttimeinsuite;
    private String testname;
    private HashMap<String,Object> context;
    private TestSuite suiteFather;
    public void setSuiteFather(TestSuite suiteFather) {
        this.suiteFather = suiteFather;
    }
    public TestSuite getSuiteFather() {
        return suiteFather;
    }
    public void printSuiteTrace(Test test, TestReport testReport) {
        if (test instanceof TestCase){
            if (firsttimeinsuite) {
                testReport.print(getNameFather());
                testReport.insertHSeparator();
            }
            firsttimeinsuite=false;
        }else firsttimeinsuite=true;
    }
    private boolean existsTest(Test newTest) {
        return testlineitem.contains(newTest);
    }
    private String insertAncestor(TestSuite father){
        StringBuilder sb= new StringBuilder(testname);
        sb.insert(0,father.getNameFather()+".");
        return sb.toString();
    }
    private String getNameFather() {
        TestSuite father=getSuiteFather();
        String retval=testname;
        if (father!=null){
            retval=insertAncestor(father);
        }
        return retval;
    }
    public TestSuite(String testname) {
        this.testname=testname;
        this.testlineitem=new Vector<Test>();
        this.firsttimeinsuite=true;
    }

    public TestSuite() {
        this.testname=null;
        this.testlineitem=new Vector<Test>();
        this.firsttimeinsuite=true;
    }
    public void addTest(Test test) {
        if(!existsTest(test)){
            if (test instanceof TestSuite) ((TestSuite)test).setSuiteFather(this);
            testlineitem.add(test);
        }
    }
    public void tearingDown(Throwable exception){
        try {
            tearDown();
        } catch (Throwable tearingDown) {
            if (exception == null) exception = tearingDown;
        }
    }
    public HashMap<String, Object> getContext() {
        return context;
    }
    @Override
    public void setUp() {}
    @Override
    public void tearDown() {}

    @Override
    public int countTestCases() {
        int count = 0;
        for (Test test : testlineitem) {
            count += test.countTestCases();
        }
        return count;
    }
    @Override
    public void run(TestReport testReport) throws Throwable {
        Timer timer= new Timer(System.nanoTime());
        if (testReport.testsuiteNameMatchRegularExpression(this)){
            Throwable exception = null;
            setUp();
            try{
                for (Test test : testlineitem){
                    printSuiteTrace(test,testReport);
                    test.run(testReport);
                }
            }catch (Throwable running) {
                exception = running;
            } finally {
                tearingDown(exception);
                testReport.print(testname+": "+timer.getTime()+"\n");
            }
            if (exception != null) throw exception;
        }
    }
}
