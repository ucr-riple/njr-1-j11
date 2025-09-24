package fw.numbers;

public class EqualMatcher<T extends Number> extends ComparisonLayer<T> {

  public EqualMatcher(final NumberMatcher<T> base) {
    super(base);
  }

  @Override
  protected boolean evaluateLayer(final T baseValue, final T testValue) {
    return baseValue.equals(testValue);
  }

  @Override
  protected String getSign() {
    return "=";
  }

}
