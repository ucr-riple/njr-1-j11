package ar.fiuba.tecnicas.framework.JTest;

import java.util.HashMap;
/*
Responsabilidad: Definir una interfaz para los elementos de la composicion
 */
public interface Test{
    public abstract void setUp();
    public abstract void tearDown();
    public abstract void run(TestReport testReport) throws Throwable;
    public abstract int countTestCases();
}
