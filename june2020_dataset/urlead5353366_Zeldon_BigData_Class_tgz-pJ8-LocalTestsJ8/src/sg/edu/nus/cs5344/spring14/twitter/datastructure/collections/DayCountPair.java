package sg.edu.nus.cs5344.spring14.twitter.datastructure.collections;


import org.apache.hadoop.io.VIntWritable;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.Copyable;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Day;

public class DayCountPair extends Pair<Day, VIntWritable> implements Copyable<DayCountPair> {

	public DayCountPair() {
		super(new Day(), new VIntWritable());
	}

	public DayCountPair(Day day, VIntWritable count) {
		super(day, count);
	}

	public Day getDay() {
		return getFirst();
	}

	public VIntWritable getCount() {
		return getSecond();
	}

	@Override
	public DayCountPair copy() {
		return new DayCountPair(getDay(), getCount());
	}
}
