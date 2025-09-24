package ar.fiuba.tecnicas.framework.Pruebas.Casos2;

import ar.fiuba.tecnicas.framework.JTest.*;

public class CaseUse1 implements TestCreator {
    @Override
    public Test getTest() throws Exception {
        TestSuite suite =new TestSuite("TS1");
        TestCase test1 = new MyTestCase("T1");
        TestCase test2 = new MyTestCase("T2");
        TestCase test3 = new MyTestCase("T3");

        test1.addTag("SLOW");
        test3.addTag("SLOW");

        suite.addTest(test1);
        suite.addTest(test2);
        suite.addTest(test3);
        return suite;
    }

    // para correr el test se paso como parametros el tag: SLOW
    // mediante el parametro: -tctags SLOW
    // y sólo se ejecuta el test T1 y T3
    public static void main(String args[]) {
        TestCreator creatorTest = new CaseUse1();
        TestRunner.setCreatorTest(creatorTest);
        TestRunner.main(args);
    }
}
