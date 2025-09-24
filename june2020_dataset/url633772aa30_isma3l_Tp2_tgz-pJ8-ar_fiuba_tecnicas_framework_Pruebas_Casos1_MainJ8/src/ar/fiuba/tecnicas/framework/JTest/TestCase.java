package ar.fiuba.tecnicas.framework.JTest;
/*
Responsabilidad: Ejecutar test con metodos que permitan ejecutar algo antes y algo despues del test
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class TestCase extends Assert implements Test{
    private List<String> tags;
    private boolean skip;
    private String testname;
    private HashMap<String,Object> context;

    public String getTestname() {
        return testname;
    }
    public HashMap<String, Object> getContext() {
        return context;
    }
    public void tearingDown(Throwable exception){
        try {
            tearDown();
        } catch (Throwable tearingDown) {
            if (exception == null) exception = tearingDown;
        }
    }
    public TestCase(String testname) {
        tags= new ArrayList<String>();
        skip = false;
        this.testname = testname;
        context=new HashMap<String,Object>();
    }
    public TestCase(String testname,List<String> tags) {
        this.tags=tags;
        skip = false;
        this.testname = testname;
        context=new HashMap<String,Object>();
    }
    @Override
    public void setUp() {}
    @Override
    public void tearDown() {}
    @Override
    public void run(TestReport testReport) {
        testReport.run(this);
        testReport.setFirsttimeintest(false);
    }
    @Override
    public int countTestCases() {
        return 1;
    }
    public abstract void runTest();

    public void runTestSequence()throws Throwable{
        Throwable exception = null;
        setUp();
        try {
            runTest();
        } catch (Throwable running) {
            exception = running;
        } finally {
            tearingDown(exception);
        }
        if (exception != null) throw exception;
    }
    public void addTag(String tag){
        tags.add(tag);
    }
    public void addAllTags(List<String> tags){
        this.tags.addAll(tags);
    }
    public boolean containsTag(String tag){
        return tags.contains(tag);
    }
    public boolean containsAllTags(List<String> tags){
        return this.tags.containsAll(tags);
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getTestname();
    }


    public void skip() {
        skip = true;
    }

    public void unSkype() {
        skip = false;
    }

    public boolean getSkip() {
        return skip;
    }
}
