package fw.numbers;

public class GreaterMatcher<T extends Number> extends ComparisonLayer<T> {

  public GreaterMatcher(final NumberMatcher<T> base) {
    super(base);
  }

  @Override
  protected boolean evaluateLayer(final T baseValue, final T testValue) {
    if (baseValue instanceof Integer) {
      return testValue.intValue() > baseValue.intValue();
    } else if (baseValue instanceof Float) {
      return testValue.floatValue() > baseValue.floatValue();
    } else if (baseValue instanceof Double) {
      return testValue.doubleValue() > baseValue.doubleValue();
    } else if (baseValue instanceof Long) {
      return testValue.longValue() > baseValue.longValue();
    } else if (baseValue instanceof Short) {
      return testValue.shortValue() > baseValue.shortValue();
    } else {
      throw new IllegalArgumentException("API cannot compare type " + baseValue.getClass());
    }
  }

  @Override
  protected String getSign() {
    return ">";
  }

}
