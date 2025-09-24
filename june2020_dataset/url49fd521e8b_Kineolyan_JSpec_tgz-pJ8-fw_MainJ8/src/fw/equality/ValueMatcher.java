package fw.equality;

import fw.Log;
import fw.Matcher;

public class ValueMatcher implements Matcher<Object> {

  private final Object _value;

  public ValueMatcher(final Object value) {
    if (null == value) {
      throw new IllegalArgumentException("Cannot create a ValueMatcher with null.");
    }

    _value = value;
  }

  @Override
  public boolean evaluate(final Object value) {
    return _value.equals(value);
  }

  @Override
  public void printSuccess(final Object value) {
    Log.success("Expected: <value> equals %s", _value);
  }

  @Override
  public void raiseFailure(final Object value) {
    Log.error("Expected: <value> equals %s%nActual: %s", _value, value);
  }

  @Override
  public void printNegationSuccess(final Object value) {
    Log.success("Not expected: <value> equals %s%nActual: %s", _value, value);
  }

  @Override
  public void raiseNegationFailure(final Object value) {
    Log.error("Not expected: <value> equals %s", _value);
  }

}
