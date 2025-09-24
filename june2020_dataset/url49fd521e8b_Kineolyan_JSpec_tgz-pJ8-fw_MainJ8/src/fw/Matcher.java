package fw;

public interface Matcher<T> {

  public boolean evaluate(final T value);

  public void printSuccess(final T value);

  public void printNegationSuccess(final T value);

  public void raiseFailure(final T value);

  public void raiseNegationFailure(final T value);
}
