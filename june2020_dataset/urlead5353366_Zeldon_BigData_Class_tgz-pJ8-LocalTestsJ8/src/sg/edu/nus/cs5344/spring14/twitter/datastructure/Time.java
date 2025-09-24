package sg.edu.nus.cs5344.spring14.twitter.datastructure;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.io.WritableComparable;

public class Time implements WritableComparable<Time>, Copyable<Time> {

	/**
	 * The actual state of the object
	 */
	private Date date = new Date(0);

	/**
	 * Create a new Time object specified by the given time stamp.
	 * @param timeInMs the milliseconds since January 1, 1970, 00:00:00 GMT.
	 */
	public Time(long timeInMs) {
		date = new Date(timeInMs);
	}

	public Time() {
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(date.getTime());
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		date = new Date(in.readLong());
	}

	@Override
	public int compareTo(Time o) {
		return date.compareTo(o.date);
	}

	@Override
	public Time copy() {
		return new Time(date.getTime());
	}

	public int getDaysBetween(Time other) {
		return (int)((date.getTime() - other.date.getTime()) / (1000*60*60*24L));
	}

	public Day getDay() {
		return new Day(this);
	}

	@Override
	public String toString() {
		return date.toString();
	}

	public Date getDateCopy() {
		return new Date(date.getTime());
	}
}
