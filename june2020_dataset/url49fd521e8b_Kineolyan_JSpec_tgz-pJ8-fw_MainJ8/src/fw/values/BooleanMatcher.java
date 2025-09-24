package fw.values;

import fw.Log;
import fw.Matcher;

public class BooleanMatcher implements Matcher<Boolean> {
  public static final BooleanMatcher TRUE = new BooleanMatcher(true);
  public static final BooleanMatcher FALSE = new BooleanMatcher(false);

  private final boolean _expectedValue;

  private BooleanMatcher(final boolean value) {
    _expectedValue = value;
  }

  @Override
  public boolean evaluate(final Boolean value) {
    return _expectedValue == value.booleanValue();
  }

  @Override
  public void printSuccess(final Boolean value) {
    success(value);
  }

  @Override
  public void raiseFailure(final Boolean value) {
    error(value);
  }

  @Override
  public void printNegationSuccess(final Boolean value) {
    success(value);
  }

  @Override
  public void raiseNegationFailure(final Boolean value) {
    error(value);
  }

  private void success(final Boolean value) {
    Log.success("Got %s as expected", value);
  }

  private void error(final Boolean value) {
    Log.error("Expected: %s%nActual: %s", Boolean.valueOf(_expectedValue), value);
  }

}
