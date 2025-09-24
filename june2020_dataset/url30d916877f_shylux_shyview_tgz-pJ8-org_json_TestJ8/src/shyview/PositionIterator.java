package shyview;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class PositionIterator<E> implements ListIterator<E>, Iterator<E> {
	List<E> source;
	private int index = 0;
	
	public PositionIterator(List<E> parList) {
		source = parList;
	}
	
	/*
	 * Returns the element at the current index of the iterator.
	 * @return Element at the current index
	 */
	public E current() throws NoSuchElementException {
		if (source.size() == 0) throw new NoSuchElementException();
		return source.get(index);
	}
	
	/*
	 * Returns the element with the given offset depending on the current index without modifying the index.
	 * @return Element with the given offset to the current index
	 */
	public E preview(int offset) throws NoSuchElementException  {
		int requestedItem = index + offset;
		if (requestedItem < 0 || requestedItem >= source.size()) throw new NoSuchElementException();
		return source.get(requestedItem);
	}
	public int getOffset(E element) {
		int i = source.indexOf(element);
		return i - index;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int parIndex) throws ArrayIndexOutOfBoundsException {
		if (parIndex < 0 || parIndex >= source.size()) throw new ArrayIndexOutOfBoundsException();
		index = parIndex;
	}

	@Override
	public void add(E e) {
		throw new UnsupportedOperationException();
	}
	
	public boolean hasCurrent() {
		return (source.size() > 0) ? true : false;
	}

	@Override
	public boolean hasNext() {
		return (index+1 < source.size()) ? true : false;
	}

	@Override
	public boolean hasPrevious() {
		return (index-1 >= 0) ? true : false;
	}

	@Override
	public E next() throws NoSuchElementException {
		if (index+1 >= source.size()) 
			throw new NoSuchElementException();
		index += 1;
		return source.get(index);
	}

	@Override
	public int nextIndex() {
		return index+1;
	}

	@Override
	public E previous() throws NoSuchElementException {
		if (index-1 < 0) throw new NoSuchElementException();
		index -= 1;
		return source.get(index);
	}

	@Override
	public int previousIndex() {
		return index-1;
	}

	@Override
	public void remove() {
		source.remove(index);
		if (index > 0) index -= 1;
	}

	@Override
	public void set(E e) {
		source.set(index, e);
	}

}
