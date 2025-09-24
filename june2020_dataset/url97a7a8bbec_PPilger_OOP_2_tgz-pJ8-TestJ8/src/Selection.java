import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Eine Selektion von Elementen. Welche Elemente sichtbar sind, wird mit
 * Selector-Objekten bestimmt.
 * 
 * @author Peter Pilgerstorfer
 * 
 */
public class Selection<T> implements Iterable<T>, Serializable {
	private static final long serialVersionUID = 1L;

	private List<T> list;
	private List<T> removed;
	private transient List<Selector<T>> selectors;

	public Selection() {
		this.list = new ArrayList<T>();
		this.removed = new ArrayList<T>();
		this.selectors = new ArrayList<Selector<T>>();
	}

	/**
	 * Erstelle eine neue Sicht die auf den selben Daten wie <code>base</code>
	 * arbeitet. Die uebergebenen Selektoren werden zusaetzlich zu den in
	 * <code>base</code> bestehenden uebernommen.
	 * 
	 * @param base
	 * @param selectors
	 */
	public Selection(Selection<T> base, List<Selector<T>> selectors) {
		this.list = base.list;
		this.removed = base.removed;
		this.selectors = selectors;
		this.selectors.addAll(base.selectors);
	}

	/**
	 * @return das erste selektierte Element
	 */
	public T getFirst() {
		Iterator<T> iter = iterator();
		if (iter.hasNext()) {
			return iterator().next();
		}
		return null;
	}

	/**
	 * @return die aktuelle Selektion als Liste.
	 */
	public List<T> asList() {
		List<T> list = new ArrayList<T>();

		for (T element : this) {
			list.add(element);
		}

		return list;
	}

	/**
	 * Fuegt ein Element zur Liste hinzu.
	 * 
	 * @param element
	 */
	public boolean add(T element) {
		return list.add(element);
	}

	/**
	 * Entfernt alle selektierten Elemente.
	 * 
	 * @return die Anzahl der entfernten Elemente
	 */
	public int remove() {
		int removed = 0;

		Iterator<T> iter = iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
			removed++;
		}

		return removed;
	}

	/**
	 * Stellt alle selektierten, geloeschten Elemente wieder her.
	 */
	public void restore() {
		Iterator<T> removedIter = removedIterator();

		while (removedIter.hasNext()) {
			T element = removedIter.next();
			removedIter.remove();
			list.add(element);
		}
	}

	/**
	 * @return Anzahl der selektierten Elemente
	 */
	public int count() {
		int count = 0;
		Iterator<T> iter = iterator();

		while (iter.hasNext()) {
			iter.next();
			count++;
		}

		return count;
	}

	/**
	 * @param element
	 * @return true, wenn alle Selektoren das Element selektieren, false
	 *         anderenfalls.
	 */
	public boolean selected(T element) {
		for (Selector<T> selector : selectors) {
			if (!selector.select(element)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gibt die Selection im Format einer gewoehnlichen java.util.Collection
	 * zurueck.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<T> iter = iterator();

		builder.append('[');

		if (iter.hasNext()) {
			builder.append(iter.next());
		}
		while (iter.hasNext()) {
			builder.append(", ");
			builder.append(iter.next());
		}

		builder.append(']');

		return builder.toString();
	}

	/**
	 * Initialisiert die Selektoren, da Selektoren nicht serialisierbar sind.
	 * 
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		selectors = new ArrayList<Selector<T>>();
	}

	/**
	 * Erstellt einen neuen Iterator, der alle selektierten Elemente
	 * durchlaeuft.
	 * 
	 * Elemente die von diesem Iterator entfernt werden, koennen mit
	 * <code>restore</code> wiederhergestellt werden.
	 */
	@Override
	public Iterator<T> iterator() {
		return new SelectionIterator(list, removed);
	}

	/**
	 * Erstellt einen neuen Iterator, der alle selektierten, geloeschten
	 * Elemente durchlaeuft.
	 * 
	 * Elemente die von diesem Iterator entfernt werden, sind nicht
	 * wiederherstellbar!
	 */
	public Iterator<T> removedIterator() {
		return new SelectionIterator(removed);
	}

	/**
	 * Repraesentiert einen Iterator, der durch alle selektierten Elemente
	 * iteriert.
	 * 
	 * @author Peter Pilgerstorfer
	 * 
	 */
	private class SelectionIterator implements Iterator<T> {
		private Collection<T> removed; // Collection aller entfernten Elemente
		private ListIterator<T> sourceIter; // der Iterator ueber alle Elemente
		private T current; // das aktuelle Element
		private T next; // das naechste selektierte Element, bzw. null am Ende

		public SelectionIterator(List<T> source) {
			this(source, null);
		}

		/**
		 * @param source
		 * @param removed
		 *            die Collection wo entfernte Elemente gespeichert werden
		 */
		public SelectionIterator(List<T> source, Collection<T> removed) {
			this.sourceIter = source.listIterator();
			this.current = null;
			this.next = nextSelected();
			this.removed = removed;
		}

		/**
		 * @return das naechste selektierte Element
		 */
		private T nextSelected() {
			while (sourceIter.hasNext()) {
				T next = sourceIter.next();

				if (selected(next)) {
					return next;
				}
			}

			return null;
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public T next() {
			current = next;
			next = nextSelected();

			return current;
		}

		@Override
		public void remove() {
			T previous = null;

			// Der Iterator steht schon auf der Position des naechsten
			// Elementes. Daher muss zuerst wieder zur aktuellen Position
			// zurueckgekehrt werden.
			while (sourceIter.hasPrevious() && previous != current) {
				previous = sourceIter.previous();
			}

			sourceIter.remove();

			// Der Iterator wird wieder aufs naechste Element gesetzt
			nextSelected();

			if (removed != null) {
				removed.add(current);
			}
		}
	}
}
