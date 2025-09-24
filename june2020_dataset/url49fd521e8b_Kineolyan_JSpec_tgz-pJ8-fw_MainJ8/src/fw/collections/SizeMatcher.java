package fw.collections;

import java.util.Collection;

import fw.Log;
import fw.Matcher;

public final class SizeMatcher implements Matcher<Collection<?>> {

  private static enum Comparator {
    EQUAL("==") {
      @Override
      boolean compare(final int collectionSize, final int expectedSize) {
        return collectionSize == expectedSize;
      }
    },
    GREATER_THAN(">=") {
      @Override
      boolean compare(final int collectionSize, final int expectedSize) {
        return collectionSize >= expectedSize;
      }
    },
    LOWER_THAN("<=") {
      @Override
      boolean compare(final int collectionSize, final int expectedSize) {
        return collectionSize <= expectedSize;
      }
    };

    private final String _symbol;

    private Comparator(final String symbol) {
      _symbol = symbol;
    }

    abstract boolean compare(final int collectionSize, final int expectedSize);

    @Override
    public String toString() {
      return _symbol;
    }
  }

  private final int _expectedSize;
  private Comparator _comparator = Comparator.EQUAL;

  public SizeMatcher(final int size) {
    _expectedSize = size;
  }

  public SizeMatcher atLeast() {
    _comparator = Comparator.GREATER_THAN;
    return this;
  }

  public SizeMatcher atMost() {
    _comparator = Comparator.LOWER_THAN;
    return this;
  }

  @Override
  public boolean evaluate(final Collection<?> collection) {
    return _comparator.compare(collection.size(), _expectedSize);
  }

  @Override
  public void printSuccess(final Collection<?> collection) {
    Log.success("Expected: <collection>.size %s %s", _comparator, Integer.valueOf(_expectedSize));
  }

  @Override
  public void raiseFailure(final Collection<?> collection) {
    Log.error("Expected: <collection>.size %s %d%nActual: %d"
        , _comparator, Integer.valueOf(_expectedSize), Integer.valueOf(collection.size()));
  }

  @Override
  public void printNegationSuccess(final Collection<?> collection) {
    Log.success("Not expected: <collection>.size %s %d%nActual: %d"
        , _comparator, Integer.valueOf(_expectedSize), Integer.valueOf(collection.size()));
  }

  @Override
  public void raiseNegationFailure(final Collection<?> colletion) {
    Log.error("Not expected: <collection>.size %s %s"
        , _comparator, Integer.valueOf(_expectedSize));
  }

}
