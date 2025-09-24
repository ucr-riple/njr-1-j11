package sg.edu.nus.cs5344.spring14.twitter.Jobs;

import static java.lang.Math.max;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.FILTER_FIRST_DAY;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.NUM_BEST_TRENDS;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.TREND_CHI_THRESHOLD;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.TREND_LOOKBACK;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.TREND_MIN_DAYLY_TWEETS;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.TREND_MISSING_DAY_TOLLERANCE;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.TREND_USE_ALT_CHI;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.TREND_WEEKDAY_BOOST;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.Day;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Hashtag;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Trend;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Trend.TrendsBuilder;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.DayCountPair;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.DayHashtagPair;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.TopKList;

public class JobCFindTrends {

	public static class MapperImpl extends Mapper<DayHashtagPair, VIntWritable, Hashtag, DayCountPair> {
		@Override
		protected void map(DayHashtagPair dayHashPair, VIntWritable count, Context context) throws IOException,
				InterruptedException {
			context.write(dayHashPair.getHashtag(), new DayCountPair(dayHashPair.getDay(), count));
		}
	}

	public static class ReducerImpl extends Reducer<Hashtag, DayCountPair, Hashtag, Trend> {

		private Set<Integer> validDays = new HashSet<Integer>();

		private TopKList<Trend> topKTrends = new TopKList<Trend>(NUM_BEST_TRENDS);

		@SuppressWarnings("unchecked")
		// VInt is not properly genericfied
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			Configuration conf = context.getConfiguration();
			Set<Day> includedDaysSet = JobEFilterTweets.loadIncludedDays(conf);
			List<Day> includedDaysList = new ArrayList<Day>(includedDaysSet);
			Collections.sort(includedDaysList);

			int lookbackStartIndex = 0;
			for (int i = 0; i < includedDaysList.size(); i++) {
				Day day = includedDaysList.get(i);
				// Skip first days to avoid classify popular as trending.
				if (day.get() <= FILTER_FIRST_DAY + TREND_LOOKBACK) {
					System.out.println("Skipping first days: " + day);
					continue;
				}

				// Find the first day within the lookback of this day
				while (includedDaysList.get(lookbackStartIndex).get() < day.get() - TREND_LOOKBACK) {
					lookbackStartIndex++;
				}

				int samples = i - lookbackStartIndex;
				if (samples >= TREND_LOOKBACK - TREND_MISSING_DAY_TOLLERANCE) {
					validDays.add(day.get());
				} else {
					System.out.println("Skipping day due to missing data: " + day);
				}
			}

			System.out.println("Num Valid Days:" + validDays.size());
		}

		@Override
		protected void reduce(Hashtag hashtag, Iterable<DayCountPair> counts, Context context) throws IOException,
				InterruptedException {

			Deque<DayCountPair> prevDays = new ArrayDeque<DayCountPair>();

			Trend bestTrend = null;
			TrendsBuilder builder = null;

			for (DayCountPair pair : counts) {
				if (!validDays.contains(pair.getDay().get())) {
					prevDays.add(pair.copy());
					continue;
				}

				int thisDay = pair.getDay().get();

				// Remove days that are too old
				while (prevDays.size() > 0 && prevDays.peek().getDay().get() < thisDay - TREND_LOOKBACK) {
					prevDays.poll();
				}

				// Calculate chi squared chi^2 = (e-o)^2/e
				// See http://blogs.ischool.berkeley.edu/i290-abdt-s12/
				// Lecture 6 slide 10-13
				double expected = calcExpected(prevDays, thisDay);
				double observed = pair.getCount().get();
				double denominator = TREND_USE_ALT_CHI ? observed : expected;
				double chiSq = ((observed - expected) * (observed - expected)) / denominator;

				// Detect trends, build Trend objects, and keep the best
				if (chiSq > TREND_CHI_THRESHOLD && observed > expected) {
					if (builder == null) {
						// First day of trend
						builder = new TrendsBuilder(hashtag);
					} else if (prevDays.isEmpty() || thisDay - prevDays.peekLast().getDay().get() != 1) {
						// Handle quiet days between trends
						bestTrend = updateBestTrend(bestTrend, builder);
						builder = new TrendsBuilder(hashtag);
					}
					builder.withDay(pair, chiSq);
				} else if (builder != null) {
					// First day not trending
					bestTrend = updateBestTrend(bestTrend, builder);
					builder = null;
				}

				prevDays.add(pair.copy());
			}

			if (bestTrend != null) {
				topKTrends.add(bestTrend);
			}
		}

		private Trend updateBestTrend(Trend bestTrend, TrendsBuilder builder) {
			Trend candidateTrend = builder.build();
			if (bestTrend == null || bestTrend.compareTo(candidateTrend) < 0) {
				bestTrend = candidateTrend;
			}
			return bestTrend;
		}

		private double calcExpected(Queue<DayCountPair> prevDays, int thisDay) {
			double sum = 0.0;
			double numDays = 0.0;
			for (DayCountPair prevPair : prevDays) {
				boolean isSameWeekday = (prevPair.getDay().get() - thisDay) % 7 == 0;
				double factor = isSameWeekday ? TREND_WEEKDAY_BOOST : 1.0;
				int value = max(TREND_MIN_DAYLY_TWEETS, prevPair.getCount().get());
				sum += value * factor;
				numDays += factor;
			}
			if (numDays > 0.1) {
				return max(sum / numDays, TREND_MIN_DAYLY_TWEETS);
			} else {
				return TREND_MIN_DAYLY_TWEETS;
			}
		}

		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			for (Trend trend : topKTrends.sortedList()) {
				context.write(trend.getHashTag(), trend);
			}
		}
	}
}
