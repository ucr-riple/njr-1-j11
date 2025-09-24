package sg.edu.nus.cs5344.spring14.twitter.datastructure.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.hadoop.io.WritableComparable;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.Copyable;

/**
 * Class that maintain a list to the largest K entries. Elements are compared
 * using their natural order. It only support adding elements not removing them.
 * 
 * @author Tobias Bertelsen
 * 
 * @param <E>
 */
public class TopKList<E extends WritableComparable<? super E> & Copyable<E>>
implements Iterable<E> {

	private final int size;
	private final PriorityQueue<E> queue;

	/**
	 * Creates a new Top-K list. It maintains the k-greatest elements added to
	 * it.
	 * 
	 * @param size
	 *            The parameter k
	 */
	public TopKList(final int size) {
		if (size < 1) {
			throw new IllegalArgumentException();
		}
		this.size = size;
		queue = new PriorityQueue<E>(size);
	}

	/**
	 * Adds an element to the top 20 list, unless it is lesser than all elements
	 * in the list and the list is full.
	 * 
	 * @param e
	 *            The element to add
	 */
	public void add(final E e) {
		// We must add copies to ensure encapsulation.
		if (queue.size() < size) {
			queue.add(e.copy());
		} else {
			// If e is in top 20, remove lowest element and add e.
			final E buttom = queue.peek();
			if (buttom.compareTo(e) < 0) {
				queue.poll();
				queue.add(e.copy());
			}
		}
	}

	/**
	 * Returns an iterator over a set of element <b>without</b> any particular
	 * order.
	 */
	@Override
	public Iterator<E> iterator() {
		return new IteratorWithoutRemove<E>(queue.iterator());
	}

	/**
	 * Returns an iterator of the set of elements in order starting with largest
	 * element.
	 */
	public List<E> sortedList() {
		final List<E> list = new ArrayList<E>(queue);
		Collections.sort(list);
		Collections.reverse(list);
		return list;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("[");
		final String seperator = ", ";

		final List<E> list = sortedList();
		for (final E e : list) {
			sb.append(e);
			sb.append(seperator);
		}

		// Fence-post, Remove last seperator
		if (!list.isEmpty()) {
			sb.delete(sb.length() - seperator.length(), sb.length());
		}

		sb.append("]");
		return sb.toString();
	}
}
