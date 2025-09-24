package sg.edu.nus.cs5344.spring14.twitter.datastructure.collections;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.WritableComparable;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.Copyable;

/**
 * A list implementation that implements writable.
 * The list is meant to be extended for specific types.
 * <b>
 * Greatly inspired by: http://stackoverflow.com/q/11306650/722929
 * @author Tobias Bertelsen
 *
 */
public abstract class ListWritable<E extends WritableComparable<? super E> & Copyable<E>>
implements Iterable<E>, WritableComparable<ListWritable<E>> {

	private E prototype;
	private List< E > list = new ArrayList<E>();

	public ListWritable(E type) {
		prototype = type;
	}

	/**
	 * Adds an element to the list.
	 * The element will be copied, to ensure its contents is
	 * not accidentally overwritten by Hadoop.
	 * @param e The element to add
	 */
	public void add(E e) {
		list.add(e.copy());
	}

	public void addAll(Iterable<E> it) {
		for (E e : it) {
			add(e);
		}
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(E e) {
		return list.contains(e);
	}

	/**
	 * Returns the backing list object. Use with great care, since it
	 * breaks encapsulation
	 * @return
	 */
	//	public List<T> getList() {
	//		return list;
	//	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(list.size());
		for (E t : list) {
			t.write(out);
		}
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		list.clear();
		int length = in.readInt();
		for (int i = 0; i < length; i++) {
			prototype.readFields(in);
			// add() creates a copy:
			add(prototype);
		}
	}

	@Override
	public int hashCode() {
		return prototype.getClass().hashCode() + 31*list.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ListWritable) {
			// We can't check the generic type due to type erasure
			@SuppressWarnings("rawtypes")
			ListWritable other = (ListWritable) obj;
			if (prototype.getClass().equals(other.prototype.getClass())) {
				return list.equals(other.list);
			}
		}
		return false;
	}

	@Override
	public int compareTo(ListWritable<E> o) {
		// Compare lengths
		if (size() != o.size()) {
			return size() - o.size();
		}

		// Compare list contents. Break on first inequality
		int comparisson = 0;
		List<E> otherList = o.list;
		for (int i = 0; i < list.size(); i++) {
			comparisson = list.get(i).compareTo(otherList.get(i));
			if (comparisson != 0) {
				return comparisson;
			}
		}

		// Everything is equal.
		return 0;
	}

	@Override
	public String toString() {
		return list.toString();
	}
}
