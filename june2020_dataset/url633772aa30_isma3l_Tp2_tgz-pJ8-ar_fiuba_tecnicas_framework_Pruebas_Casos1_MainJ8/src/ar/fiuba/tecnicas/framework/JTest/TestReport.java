package ar.fiuba.tecnicas.framework.JTest;

import java.util.ArrayList;
import java.util.List;

public class TestReport {
    private List<TestListener> testListeners;
    private int runTests;
    private int oktest;
    private int errortest;
    private int failedtest;
    private boolean firsttimeintest;
    private PatternRecognizer recognizerExpressionsTestcase;
    private PatternRecognizer recognizerExpressionsTestsuite;
    private RecognizerTag recognizerTags;

    public void setFirsttimeintest(boolean firsttimeintest) {
        this.firsttimeintest = firsttimeintest;
    }
    public TestReport() {
        testListeners = new ArrayList<TestListener>();
        runTests = 0;
        oktest=0;
        errortest=0;
        failedtest=0;
        firsttimeintest=true;
        recognizerExpressionsTestcase = null;
        recognizerExpressionsTestsuite=null;
        recognizerTags = null;
    }

    public void initializeRecognizerExpression(String testcaseregexp,String testsuiteregexp) {
        recognizerExpressionsTestcase = new PatternRecognizer(testcaseregexp);
        recognizerExpressionsTestsuite= new PatternRecognizer(testsuiteregexp);
    }

    public void setRecognizerExpressionsTestcase(PatternRecognizer recognizer) {
        recognizerExpressionsTestcase = recognizer;
    }

    public void setRecognizerExpressionsTestsuite(PatternRecognizer recognizer) {
        recognizerExpressionsTestsuite = recognizer;
    }

    public void setRecognizerTag(RecognizerTag recognizerTags) {
        this.recognizerTags = recognizerTags;
    }

    public void addSuccess(TestCase test,Timer timer) {
        runTests++;
        for (TestListener testListener : testListeners) {
            testListener.addSuccess(test, timer.getTime());
        }
    }
    public void addFailure(Test test,Timer timer, Throwable throwable) {
        failedtest++;
        runTests++;
        for (TestListener testListener : testListeners) {
            testListener.addFailure(test, timer.getTime(), throwable);
        }
    }
    public void addError(Test test,Timer timer, Throwable throwable) {
        errortest++;
        runTests++;
        for (TestListener testListener : testListeners) {
            testListener.addError(test, timer.getTime(), throwable);
        }
    }
    public void insertHSeparator(){
        for (TestListener testListener : testListeners) {
            testListener.insertHSeparator();
        }
    }
    public void print(String messsage){
        for (TestListener testListener : testListeners) {
            testListener.print(messsage);
        }
    }
    public void addListener(TestListener listener) {
        testListeners.add(listener);
    }
    public void run(final TestCase test) {
        Timer timer= new Timer(System.nanoTime());
        if(validateTestCase(test)){
            try {
                 test.runTestSequence();
                 addSuccess(test,timer);
            } catch (AssertionError assertionError) {
                addFailure(test,timer, assertionError);
            } catch (Throwable exception) {
                addError(test,timer, exception);
            }
        }
    }

    private boolean validateTestCase(TestCase test) {
        return (testNameMatchRegularExpression(test)) && validateTagTestCase(test) && !test.getSkip();
    }

    private boolean validateTagTestCase(TestCase test) {
        return recognizerTags.validate(test.getTags());
    }

    private boolean testNameMatchRegularExpression(Test test) {
        return recognizerExpressionsTestcase == null || recognizerExpressionsTestcase.validate(test.toString());
    }
    public boolean testsuiteNameMatchRegularExpression(Test test) {
        return recognizerExpressionsTestsuite == null || recognizerExpressionsTestsuite.validate(test.toString());
    }
    public boolean wasSuccessful() {
        return ( failureCount()== 0);
    }
    public int failureCount() {
        return failedtest;
    }
    public int runCount() {
        return runTests;
    }
    public int errorCount() {
        return errortest;
    }
}
