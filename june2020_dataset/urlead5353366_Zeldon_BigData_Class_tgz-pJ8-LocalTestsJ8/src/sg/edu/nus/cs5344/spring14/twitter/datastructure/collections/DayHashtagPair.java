package sg.edu.nus.cs5344.spring14.twitter.datastructure.collections;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.Copyable;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Day;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Hashtag;

public class DayHashtagPair extends Pair<Day, Hashtag> implements Copyable<DayHashtagPair> {

	public DayHashtagPair() {
		super(new Day(), new Hashtag());
	}

	public DayHashtagPair(Day day, Hashtag hashtag) {
		super(day, hashtag);
	}

	public Day getDay() {
		return getFirst();
	}

	public Hashtag getHashtag() {
		return getSecond();
	}

	@Override
	public DayHashtagPair copy() {
		return new DayHashtagPair(getDay(), getHashtag());
	}
}
