package sg.edu.nus.cs5344.spring14.twitter.datastructure.collections;

import java.util.Iterator;

/**
 * An iterator that disables {@code remove()} to ensure encapsulation.
 * @author Tobias Bertelsen
 *
 * @param <T> The element type
 */
public class IteratorWithoutRemove<T> implements Iterator<T>{

  /**
   * Actual iterate to delegate calls to.
   */
  private final Iterator<T> delegate;

  public IteratorWithoutRemove(final Iterator<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean hasNext() {
    return delegate.hasNext();
  }

  @Override
  public T next() {
    return delegate.next();
  }

  @Override
  public void remove() {
    // Disallow this operation.
    throw new UnsupportedOperationException("Remove is not supported for this Iterator.");
  }
}
