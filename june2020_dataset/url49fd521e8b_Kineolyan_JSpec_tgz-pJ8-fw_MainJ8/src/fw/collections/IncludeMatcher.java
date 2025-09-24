package fw.collections;

import java.util.Collection;

import fw.Log;
import fw.Matcher;

public class IncludeMatcher implements Matcher<Collection<?>> {

  private Object _item;

  public IncludeMatcher(final Object item) {
    _item = item;
  }

  @Override
  public boolean evaluate(final Collection<?> collection) {
    return collection.contains(_item);
  }

  @Override
  public void printSuccess(final Collection<?> collection) {
    Log.success("Expected: " + itemFound());
  }

  @Override
  public void raiseFailure(final Collection<?> value) {
    Log.error("Not expected: " + itemNotFound());
  }

  @Override
  public void printNegationSuccess(final Collection<?> value) {
    Log.success("Expected: " + itemNotFound());
  }

  @Override
  public void raiseNegationFailure(final Collection<?> value) {
    Log.error("Not expected: " + itemFound());
  }

  private String itemFound() {
    return "Found " + _item + " in collection";
  }

  private String itemNotFound() {
    return String.format("%s not found in collection", _item);
  }

}
