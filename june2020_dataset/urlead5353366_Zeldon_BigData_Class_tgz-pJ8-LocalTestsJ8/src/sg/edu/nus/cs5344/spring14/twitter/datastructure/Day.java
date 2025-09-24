package sg.edu.nus.cs5344.spring14.twitter.datastructure;

import org.apache.hadoop.io.VIntWritable;

/**
 * A class indicating on specific day
 * @author Tobias Bertelsen
 *
 */
public class Day extends VIntWritable implements Copyable<Day> {


	/**
	 * Day zero of the day index. <b>DO NOT MODIFY</b>.
	 *
	 * <p>
	 * It is specified to be 1359562320000L which is 2012-04-01.
	 * The first tweets are believed to be from 2012-04-02, so days indexes should start at
	 * 1, but DO NOT count on this!
	 * Run this code, to calculate new values:
	 * <pre>
	 * System.out.println((new SimpleDateFormat("yyyy-MM-dd")).parse("2012-04-01").getTime());
	 * </pre>
	 */
	// SERIOUSLY Do not modify! The filter is hardcoded relative to this variable
	private static final Time DAY_ZERO = new Time(1333209600000L);

	public Day(){
	}

	public Day(int value) {
		super(value);
	}
	public Day(Time time) {
		super(time.getDaysBetween(DAY_ZERO));
	}

	@Override
	public Day copy() {
		return new Day(get());
	}
}
