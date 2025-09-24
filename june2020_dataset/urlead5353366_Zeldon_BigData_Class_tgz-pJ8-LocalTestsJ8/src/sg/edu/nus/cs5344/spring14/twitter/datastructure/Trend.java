package sg.edu.nus.cs5344.spring14.twitter.datastructure;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.io.WritableComparable;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.DayCountPair;

public class Trend implements WritableComparable<Trend>, Copyable<Trend> {

	public static class TrendsBuilder {
		private boolean canUse = true;
		private List<DayCountPair> days = new ArrayList<DayCountPair>();
		private double maxChiSq = 0.0;
		private final Hashtag hashtag;

		public TrendsBuilder(Hashtag hashtag) {
			this.hashtag = hashtag;
		}

		public TrendsBuilder withDay(DayCountPair day, double chiSq) {
			ensureCanUse();

			maxChiSq = Math.max(chiSq, maxChiSq);
			days.add(day.copy());
			return this;
		}

		public Trend build() {
			ensureCanUse();
			canUse = false;
			// Ensure days are chronological
			Collections.sort(days);
			Day firstDay = days.get(0).getDay();
			Day lastDay = days.get(days.size() - 1).getDay();
			int totalCount = 0;
			for (DayCountPair day : days) {
				totalCount += day.getCount().get();
			}

			return new Trend(hashtag, firstDay, lastDay, totalCount, maxChiSq);
		}

		private void ensureCanUse() {
			if (!canUse) {
				throw new IllegalStateException("Builder can only be used once");
			}
		}
	}

	private Hashtag hashTag;
	private Day firstDay;
	private Day lastDay;
	private int totalCount;
	private double maxChiSq;

	protected Trend(Hashtag hashtag, Day firstDay, Day lastDay, int totalCount, double maxChiSq) {
		this.hashTag = hashtag;
		this.firstDay = firstDay;
		this.lastDay = lastDay;
		this.totalCount = totalCount;
		this.maxChiSq = maxChiSq;
	}

	public Trend() {
		hashTag = new Hashtag();
		firstDay = new Day();
		lastDay = new Day();
	}

	public Hashtag getHashTag() {
		return hashTag;
	}

	public Day getFirstDay() {
		return firstDay;
	}

	public Day getLastDay() {
		return lastDay;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public double getMaxChiSq() {
		return maxChiSq;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		hashTag.readFields(in);
		firstDay.readFields(in);
		lastDay.readFields(in);
		totalCount = in.readInt();
		maxChiSq = in.readDouble();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		hashTag.write(out);
		firstDay.write(out);
		lastDay.write(out);
		out.writeInt(totalCount);
		out.writeDouble(maxChiSq);
	}

	@Override
	public Trend copy() {
		return new Trend(hashTag.copy(), firstDay.copy(), lastDay.copy(), totalCount, maxChiSq);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Trend)) {
			return false;
		}

		return compareTo((Trend) obj) == 0;
	}

	@Override
	public int compareTo(Trend other) {
		int rtrn = Double.compare(maxChiSq, other.maxChiSq);
		if (rtrn != 0) {
			return rtrn;
		}
		rtrn = hashTag.compareTo(other.hashTag);
		if (rtrn != 0) {
			return rtrn;
		}
		rtrn = firstDay.compareTo(other.firstDay);
		if (rtrn != 0) {
			return rtrn;
		}
		rtrn = lastDay.compareTo(other.lastDay);
		if (rtrn != 0) {
			return rtrn;
		}
		return totalCount - other.totalCount;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Trend: ");
		sb.append(hashTag);
		sb.append(", ");
		sb.append(maxChiSq);
		sb.append(", ");
		sb.append(totalCount);
		sb.append(", ");
		sb.append(firstDay);
		sb.append(" -> ");
		sb.append(lastDay);
		return sb.toString();
	}
}
