package fw.equality;

import fw.Log;
import fw.Matcher;

public class ReferenceMatcher implements Matcher<Object> {

  private final Object _value;

  public ReferenceMatcher(final Object value) {
    if (null == value) {
      throw new IllegalArgumentException("Cannot create a ReferenceMatcher with null.");
    }

    _value = value;
  }

  @Override
  public boolean evaluate(final Object value) {
    return value == _value;
  }

  @Override
  public void printSuccess(final Object value) {
    Log.success("Got 0x%d as expected", Integer.valueOf(System.identityHashCode(_value)));
  }

  @Override
  public void raiseFailure(final Object value) {
    Log.error("Expected: 0x%d%nActual: 0x%d"
        , Integer.valueOf(System.identityHashCode(_value))
        , Integer.valueOf(System.identityHashCode(value))
        );
  }

  @Override
  public void printNegationSuccess(final Object value) {
    Log.success("Not expected: 0x%d%nActual: 0x%d"
        , Integer.valueOf(System.identityHashCode(_value))
        , Integer.valueOf(System.identityHashCode(value))
        );
  }

  @Override
  public void raiseNegationFailure(final Object value) {
    Log.error("Unexpectedly got 0x%d", Integer.valueOf(System.identityHashCode(_value)));
  }

}
