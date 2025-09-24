package ar.fiuba.tecnicas.framework.JTest;

public interface TestListener {
    public void addFailure(Test test, String time, Throwable throwable);
    public void addError(Test test, String time, Throwable throwable);
    public void addSuccess(TestCase test, String time);
    public void print(String messsage);
    public void insertHSeparator();
}
