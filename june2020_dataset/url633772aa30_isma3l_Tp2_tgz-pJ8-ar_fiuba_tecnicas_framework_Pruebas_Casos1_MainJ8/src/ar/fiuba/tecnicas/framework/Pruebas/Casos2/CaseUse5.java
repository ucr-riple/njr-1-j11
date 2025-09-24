package ar.fiuba.tecnicas.framework.Pruebas.Casos2;


import ar.fiuba.tecnicas.framework.JTest.*;

public class CaseUse5 implements TestCreator {
    @Override
    public Test getTest() throws Exception {
        TestSuite suite =new TestSuite("Suite 5");
        TestCase test1 = new MyTestCase("T1");
        TestCase test2 = new MyTestCase("T2");
        TestCase test3 = new MyTestCase("T3");
        TestCase test4 = new MyTestCase("test con mysql 1 case");
        TestCase test5 = new MyTestCase("test con mysql 2 use");
        TestCase test6 = new MyTestCase("test con mysql 3 ending");

        test1.addTag("DB");
        test2.addTag("DB");
        test3.addTag("SLOW");
        test4.addTag("DB");
        test5.addTag("DB");
        test6.addTag("-");

        suite.addTest(test1);
        suite.addTest(test2);
        suite.addTest(test3);
        suite.addTest(test4);
        suite.addTest(test5);
        suite.addTest(test6);
        return suite;
    }

    // para correr el test se pasaron como parametros:
    // -tctags DB -tcregexp mysql
    // de modo que solo se ejecuten los test que tienen el tag DB y que contengan en algun lugar
    // la palabra "mysql"
    // solo se ejecutaron los test T4 ("test con mysql 1 case") y T5 ("test con mysql 2 use")
    public static void main(String args[]) {
        TestCreator creatorTest = new CaseUse5();
        TestRunner.setCreatorTest(creatorTest);
        TestRunner.main(args);
    }
}