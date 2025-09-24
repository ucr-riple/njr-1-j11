package fw.numbers;

public class BaseNumberMatcher<T extends Number> extends NumberMatcher<T> {

  private final T _value;

  public BaseNumberMatcher(final T value) {
    _value = value;
  }

  @Override
  public T getValue() {
    return _value;
  }

  @Override
  public boolean evaluate(final T value) {
    return false;
  }

  @Override
  public String toString() {
    return " " + _value + " ";
  }

}
