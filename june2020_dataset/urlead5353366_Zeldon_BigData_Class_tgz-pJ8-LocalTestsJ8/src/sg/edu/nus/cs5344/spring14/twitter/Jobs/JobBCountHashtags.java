package sg.edu.nus.cs5344.spring14.twitter.Jobs;

import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.TREND_MIN_DAYLY_TWEETS;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.Day;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Hashtag;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Tweet;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.DayHashtagPair;

/**
 * This job counts how many times a hashtag is used in one specific day.
 *
 * Further it detects the first day of the first hashtag, and stores it in HDFS.
 * This is done EXTREMELY hacky, by merging in into the other hashtag/day
 * calculation, but it saves an entire extra run of the data, to extract just
 * one value.
 *
 * @author tobber
 *
 */
public class JobBCountHashtags {


	public static class MapperImpl extends Mapper<Tweet, NullWritable, DayHashtagPair, VIntWritable> {
		private VIntWritable ONE = new VIntWritable(1);

		@Override
		protected void map(Tweet tweet, NullWritable nothing, Context context) throws IOException, InterruptedException {
			Day day = tweet.getTime().getDay();
			for (Hashtag hashtag : tweet.getHashTagList()) {
				context.write(new DayHashtagPair(day, hashtag), ONE);
			}
		}
	}

	public static class ReducerImpl extends Reducer<DayHashtagPair, VIntWritable, DayHashtagPair, VIntWritable> {
		@Override
		protected void reduce(DayHashtagPair pair, Iterable<VIntWritable> counts, Context context) throws IOException,
				InterruptedException {
			int sum = 0;
			for (VIntWritable count : counts) {
				sum += count.get();
			}

			if (sum > TREND_MIN_DAYLY_TWEETS) {
				context.write(pair, new VIntWritable(sum));
			}
		}
	}

}
