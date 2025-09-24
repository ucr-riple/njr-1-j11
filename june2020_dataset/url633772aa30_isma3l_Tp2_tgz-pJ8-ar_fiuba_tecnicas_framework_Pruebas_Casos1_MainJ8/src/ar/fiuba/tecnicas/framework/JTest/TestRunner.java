package ar.fiuba.tecnicas.framework.JTest;

import ar.fiuba.tecnicas.framework.ArgumentValidator;
import ar.fiuba.tecnicas.framework.Usage;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/*
 Responsabilidad: Crear y correr test ya definidos o un grupo de test definidios filtrado con expresiones regulares
 */
public class TestRunner {
    public static final int SUCCESS_EXIT = 0;
    public static final int FAILURE_EXIT = 1;
    public static final int EXCEPTION_EXIT = 2;
    private ResultPrinter resultPrinter;
    private String regexpTestcase;
    private String regexpTestsuite;
    private List<String> argtags;
    private static TestCreator testCreator;

    public TestRunner() {
        this.resultPrinter= new ResultPrinter(System.out);
        regexpTestcase="";
        regexpTestsuite="";
        argtags = new ArrayList<String>();

    }

    public void setListener(TestListener listener) {

    }

    public void setRegexpTestcase(String regexpTestcase) {
        this.regexpTestcase = regexpTestcase;
    }

    public void setRegexpTestsuite(String regexpTestsuite) {
        this.regexpTestsuite = regexpTestsuite;
    }

    public void setArgtags(List<String> argtags) {
        this.argtags = argtags;
    }


    public static void setCreatorTest(TestCreator testCreator) {
        TestRunner.testCreator = testCreator;
    }

    public static void main(String args[]) {
        try {
            TestRunner testRunner = new TestRunner();
            ArgumentValidator argvalidate= new ArgumentValidator(testRunner,args);
            argvalidate.start();
            TestReport testreport=testRunner.start();
            if (!testreport.wasSuccessful()) {
                System.exit(FAILURE_EXIT);
            }
            System.exit(SUCCESS_EXIT);
        }catch (InvalidArgumentException badarg){
            Usage usage= new Usage();
            System.err.println(usage);
            System.exit(EXCEPTION_EXIT);
        }catch (PatternSyntaxException patternexcp){
            System.err.println("Invalid regular expression 's syntax");
            System.exit(EXCEPTION_EXIT);
        } catch (Throwable e) {
            System.err.println(e.getMessage());
            System.exit(EXCEPTION_EXIT);
        }
    }
    private Test getTest() throws Exception {
        return testCreator.getTest();
    }
    private TestReport start() throws Throwable {
        Test test= getTest();
        return doRun(test);
    }
    private TestReport doRun(Test suite) throws Throwable {
        TestReport result = createTestReport();
        suite.run(result);
        resultPrinter.printFooter(result);
        return result;
    }

    private TestReport createTestReport() {
        TestReport result = new TestReport();

        result.setRecognizerExpressionsTestcase(new PatternRecognizer(regexpTestcase));
        result.setRecognizerExpressionsTestsuite(new PatternRecognizer(regexpTestsuite));
        result.setRecognizerTag(new RecognizerTag(argtags));
        result.addListener(resultPrinter);
        result.addListener(new XmlPrinter());
        return result;
    }
}
