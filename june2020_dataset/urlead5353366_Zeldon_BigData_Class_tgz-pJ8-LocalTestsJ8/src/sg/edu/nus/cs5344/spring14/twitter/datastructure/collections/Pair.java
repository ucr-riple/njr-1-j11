package sg.edu.nus.cs5344.spring14.twitter.datastructure.collections;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

/**
 * A {@link Writable} pair of two writable values. The pair is comparable with
 * the fist element as the most significant member when ordering.
 * 
 * @author Tobias Bertelsen
 * 
 * @param <T> The type of the first member
 * @param <S> The type of the second member
 */
// Since we are using Hadoop 1.2.1, some writables are not genericfied,
// so we can't give the comparable interface a comparable argument
@SuppressWarnings("rawtypes")
public abstract class Pair<T extends WritableComparable, S extends WritableComparable> implements
WritableComparable<Pair<T, S>> {

	private final T first;

	private final S second;

	protected Pair(final T first, final S second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Get the first member
	 * @return the first member
	 */
	protected T getFirst() {
		return first;
	}

	/**
	 * Get the second member
	 * @return the second member
	 */
	protected S getSecond() {
		return second;
	}

	@SuppressWarnings("unchecked")
	// Using raw types. See beginning of file
	@Override
	public int compareTo(final Pair<T, S> other) {
		// The first member is the most significant
		final int compare = first.compareTo(other.first);
		if (compare != 0) {
			return compare;
		} else {
			return second.compareTo(other.second);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Pair) {
			final Pair<?, ?> other = (Pair<?, ?>) obj;
			return first.equals(other.first) && second.equals(other.second);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return first.hashCode() + 31 * second.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(first);
		sb.append(", ");
		sb.append(second);
		sb.append(")");
		return sb.toString();
	}

	@Override
	public void write(final DataOutput out) throws IOException {
		// Delegate the writing to members
		first.write(out);
		second.write(out);
	}

	@Override
	public void readFields(final DataInput in) throws IOException {
		// Delegate the reading to members
		first.readFields(in);
		second.readFields(in);
	}
}
