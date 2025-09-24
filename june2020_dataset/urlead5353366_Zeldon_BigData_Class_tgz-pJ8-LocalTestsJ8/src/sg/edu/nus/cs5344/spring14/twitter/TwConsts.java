package sg.edu.nus.cs5344.spring14.twitter;

public class TwConsts {

	/**
	 * Defines the number of previous days we compare the current day against
	 * when finding trends.
	 */
	public static final int TREND_LOOKBACK = 7;

	/**
	 * Defines the number of missing days we can tolerate not having data for.
	 */
	public static final int TREND_MISSING_DAY_TOLLERANCE = 2;

	public static final int TREND_MIN_DAYLY_TWEETS = 40;

	/**
	 * How many times more the day of the week should count, compared to other
	 * days.
	 *
	 * Zero means no change
	 *
	 * A value of 3 mean that the day 7 days ago, will count as 4 days (3 days
	 * more)
	 */
	public static final double TREND_WEEKDAY_BOOST = 3.0;

	/**
	 * The minimal chiSq scored for something to be considered a trend.
	 * Not an important parameter, as we only keep to best trends, which are
	 * way above this threshold
	 *
	 * For normal chi the flowing holds:
	 * <ul>
	 * <li> 95% significance = 3.84
	 * <li> 99% significance = 6.64
	 * </ul>
	 *
	 * Based on http://www2.lv.psu.edu/jxm57/irp/chisquar.htm
	 */
	public static final double TREND_CHI_THRESHOLD = 6.64;

	/**
	 * Whether the us the alternative chiSq metric.
	 * The alternative chiSquared proritize popular tags higher
	 * than one day peaks, by dividing with the observed value instead
	 * of the expected, which is very low for one day peaks.
	 * <pre>
	 * chiSq = (E-O)^2/E
	 * altChiSq = (E-O)^2/O
	 * where E is expected and O is observed
	 * </pre>
	 */
	public static final boolean TREND_USE_ALT_CHI = true;

	/**
	 * The number of trends to keep
	 */
	public static final int NUM_BEST_TRENDS = 50;


	/**
	 * The number of days before a trend we want to extract tweets.
	 * When we extract tweets for a given hashtags, this number determine
	 * how many days before the trend, we will get tweets.
	 */
	public static final int DAYS_BEFORE_TREND = 1;

	/**
	 * The number of days after a trend we want to extract tweets.
	 */
	public static final int DAYS_AFTER_TREND = 1;

	/**
	 * All day indexes strictly lower than this value, should not be included.
	 * Value is 158 = 2012-09-06, which is the first day after the great spikes
	 * in data frequency.
	 */
	public static final int FILTER_FIRST_DAY = 158;

	public static final int FILTER_MIN_HASHTAGS_PER_DAY = 110000;

	public static final String DAY_STATS_DATA_FOLDER_ATT = "DAY_STATS_FOLDER";

	public static final String TRENDS_DATA_FOLDER_ATT = "TRENDS_FOLDER";
}
