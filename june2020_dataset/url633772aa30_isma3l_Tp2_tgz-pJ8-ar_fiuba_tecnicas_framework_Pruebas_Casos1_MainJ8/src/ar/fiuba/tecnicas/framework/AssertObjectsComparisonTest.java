package ar.fiuba.tecnicas.framework;

import ar.fiuba.tecnicas.framework.JTest.TestReport;
import ar.fiuba.tecnicas.framework.JTest.TestSuite;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class AssertObjectsComparisonTest {
    private TestSuite testSuite;
    private TestReport testReport;

    public AssertObjectsComparisonTest() {
        testSuite= new TestSuite();
        testReport= new TestReport();
    }
     @Before
     public void setUp(){
         TestSuite assertTests=new TestSuite();
         Integer k=2;
         Integer h=2;

     }
    @Test
    public void assertTest(){

    }
}
