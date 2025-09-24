package ar.fiuba.tecnicas.framework.Pruebas.Casos2;


import ar.fiuba.tecnicas.framework.JTest.Assert;
import ar.fiuba.tecnicas.framework.JTest.TestCase;

public class MyTestCase extends TestCase {
    public MyTestCase(String testname) {
        super(testname);
    }

    @Override
    public void runTest() {
        assertFalse(false);
        assertTrue(true);
    }
}
