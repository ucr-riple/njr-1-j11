package fw.numbers;

public abstract class ComparisonLayer<T extends Number> extends NumberMatcher<T> {

  private final NumberMatcher<T> _base;

  protected ComparisonLayer(final NumberMatcher<T> base) {
    _base = base;
  }

  @Override
  public T getValue() {
    return _base.getValue();
  }

  protected NumberMatcher<T> getBase() {
    return _base;
  }

  protected abstract boolean evaluateLayer(final T baseValue, final T testValue);

  protected abstract String getSign();

  @Override
  public boolean evaluate(final T testValue) {
    return evaluateLayer(getBase().getValue(), testValue) || getBase().evaluate(testValue);
  }

  @Override
  public String toString() {
    return getSign() + _base.toString();
  }

}
