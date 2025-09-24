package ar.fiuba.tecnicas.framework.Pruebas.Casos1;


import ar.fiuba.tecnicas.framework.JTest.Test;
import ar.fiuba.tecnicas.framework.JTest.TestCreator;
import ar.fiuba.tecnicas.framework.JTest.TestSuite;

public class AllTests implements TestCreator {
    public Test getTest()throws Exception {
        TestSuite suiteMain = new TestSuite("AllTests");

        TestCreator assertTest= new AssertTest();
        TestCreator suiteATest= new SuiteATest();
        TestSuite suiteA= new TestSuite("SuiteA");
        suiteA.addTest(suiteATest.getTest());

        suiteMain.addTest(assertTest.getTest());
        suiteMain.addTest(suiteA);
        return suiteMain;
    }

}
