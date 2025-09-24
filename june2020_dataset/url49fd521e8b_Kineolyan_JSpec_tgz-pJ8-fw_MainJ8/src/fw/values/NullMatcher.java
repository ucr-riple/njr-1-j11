package fw.values;

import fw.Log;
import fw.Matcher;

public class NullMatcher implements Matcher<Object> {

  public static final NullMatcher INSTANCE = new NullMatcher();

  private NullMatcher() {}

  @Override
  public boolean evaluate(final Object value) {
    return null == value;
  }

  @Override
  public void printSuccess(final Object value) {
    success(true);
  }

  @Override
  public void raiseFailure(final Object value) {
    error(value);
  }

  @Override
  public void printNegationSuccess(final Object value) {
    success(false);
  }

  @Override
  public void raiseNegationFailure(final Object value) {
    error(value);
  }

  private void success(final boolean isNull) {
    Log.success("Got %snull value as expected", isNull ? "" : "non-");
  }

  private void error(final Object value) {
    Log.error("Expected: %snull%nActual: %s (%s)"
        , null == value ? "" : "non-"
        , null == value ? "null" : ("0x" + System.identityHashCode(value))
        , value.getClass());
  }

}
