package by.epam.lab.utils;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReverseIterator<E> implements Iterator<E> {
    ListIterator<E> iterator;
	public ReverseIterator(List<E> list) {
		super();
		iterator = list.listIterator(list.size());
	}

	@Override
	public boolean hasNext() {
		
		return iterator.hasPrevious();
	}

	@Override
	public E next() {
		
		return iterator.previous();
	}

	@Override
	public void remove() {
		iterator.remove();
		
	}

}
