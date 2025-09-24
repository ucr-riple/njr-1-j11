package fw.types;

import fw.Log;
import fw.Matcher;

public class TypeMatcher implements Matcher<Object> {

  private final Class<?> _type;

  public TypeMatcher(final Class<?> type) {
    _type = type;
  }

  @Override
  public boolean evaluate(final Object value) {
    return _type.isInstance(value);
  }

  @Override
  public void printSuccess(final Object value) {
    Log.success("Got instance of %s as expected", _type.getClass());
  }

  @Override
  public void raiseFailure(final Object value) {
    error(value);
  }

  @Override
  public void printNegationSuccess(final Object value) {
    Log.success("Not expected: %s%nActual: %s", _type, value.getClass());
  }

  @Override
  public void raiseNegationFailure(final Object value) {
    error(value);
  }

  private void error(final Object value) {
    Log.error("Expected: %s%nActual: %s", _type, value.getClass());
  }
}
