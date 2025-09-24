package ar.fiuba.tecnicas.framework.Pruebas.Casos2;


import ar.fiuba.tecnicas.framework.JTest.*;

public class CaseUse3 implements TestCreator {
    @Override
    public Test getTest() throws Exception {
        TestSuite suite =new TestSuite("Suite 3");
        TestCase test1 = new MyTestCase("T1");
        TestCase test2 = new MyTestCase("T2");
        TestCase test3 = new MyTestCase("T3");
        TestCase test4 = new MyTestCase("T4");
        TestCase test5 = new MyTestCase("T5");
        TestCase test6 = new MyTestCase("T6");

        test1.addTag("SLOW");
        test1.addTag("DB");
        test2.addTag("SLOW");
        test3.addTag("DB");
        test4.addTag("FAST");
        test5.addTag("SMOKE");
        test6.addTag("-");

        suite.addTest(test1);
        suite.addTest(test2);
        suite.addTest(test3);
        suite.addTest(test4);
        suite.addTest(test5);
        suite.addTest(test6);

        return suite;
    }


    // para correr el test se paso como parametros los tags del caso de uso: DB, FAST y SMOKE
    // mediante el parametro: -tctags DB FAST  SMOKE
    // y se ejecutaron los test T1, T3, T4, T5
    public static void main(String args[]) {
        TestCreator creatorTest = new CaseUse3();
        TestRunner.setCreatorTest(creatorTest);
        TestRunner.main(args);
    }
}