package ar.fiuba.tecnicas.framework.Pruebas.Casos2;


import ar.fiuba.tecnicas.framework.JTest.*;

public class CaseUse4 implements TestCreator {
    @Override
    public Test getTest() throws Exception {
        TestSuite suite =new TestSuite("Suite 4");
        TestCase test1 = new MyTestCase("T1");
        TestCase test2 = new MyTestCase("T2");
        TestCase test3 = new MyTestCase("T3");
        TestCase test4 = new MyTestCase("no correr");

        test1.addTag("SLOW");
        test2.addTag("FAST");
        test3.addTag("SLOW");
        test4.addTag("SLOW");

        suite.addTest(test1);
        suite.addTest(test2);
        suite.addTest(test3);
        suite.addTest(test4);
        return suite;
    }

    // para correr el test se pasaron como parametros:
    // -tctags SLOW -tcregexp T[123]
    // de modo que solo se ejecuten los test que tienen el tag SLOW y cuyo nombre
    // empiece por " no correr"
    // solo se ejecutaron los test T1 y T3
    public static void main(String args[]) {
        TestCreator creatorTest = new CaseUse4();
        TestRunner.setCreatorTest(creatorTest);
        TestRunner.main(args);
    }
}