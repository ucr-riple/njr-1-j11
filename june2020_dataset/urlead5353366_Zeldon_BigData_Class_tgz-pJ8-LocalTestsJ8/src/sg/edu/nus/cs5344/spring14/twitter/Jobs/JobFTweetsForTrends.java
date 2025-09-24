package sg.edu.nus.cs5344.spring14.twitter.Jobs;

import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.DAYS_AFTER_TREND;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.DAYS_BEFORE_TREND;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import sg.edu.nus.cs5344.spring14.twitter.ConfUtils;
import sg.edu.nus.cs5344.spring14.twitter.FileUtils;
import sg.edu.nus.cs5344.spring14.twitter.TwConsts;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Hashtag;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Trend;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Tweet;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.TweetList;

public class JobFTweetsForTrends {

	public static Map<Hashtag, Trend> loadTrends(Configuration conf) throws IOException {
		Map<Hashtag, Trend> trends = new HashMap<Hashtag, Trend>();
		FileSystem fs = FileSystem.get(conf);
		Path trendsFolder = ConfUtils.getPath(conf, TwConsts.TRENDS_DATA_FOLDER_ATT);

		Hashtag hashtag = new Hashtag();
		Trend trend = new Trend();
		for (Path path : FileUtils.getAllParts(trendsFolder, fs)) {
			Reader reader = null;
			try {
				reader = new SequenceFile.Reader(fs, path, conf);
				while (reader.next(hashtag, trend)) {
					trends.put(hashtag.copy(), trend.copy());
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		}
		return trends;
	}

	public static class MapperImpl extends Mapper<Tweet, NullWritable, Hashtag, Tweet> {
		private Map<Hashtag, Trend> trends;

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			Configuration conf = context.getConfiguration();
			trends = loadTrends(conf);
		}

		@Override
		protected void map(Tweet tweet, NullWritable nothing, Context context) throws IOException, InterruptedException {
			int day = tweet.getTime().getDay().get();
			for (Hashtag hashtag : tweet.getHashTagList()) {
				Trend trend = trends.get(hashtag);
				if (trend != null &&
						day >= trend.getFirstDay().get() - DAYS_BEFORE_TREND &&
						day <= trend.getLastDay().get() + DAYS_AFTER_TREND) {
					context.write(hashtag, tweet);
				}
			}
		}
	}

	public static class ReducerImpl extends Reducer<Hashtag, Tweet, Hashtag, TweetList> {
		@Override
		protected void reduce(Hashtag hastag, Iterable<Tweet> tweets, Context context) throws IOException,
				InterruptedException {
			TweetList list = new TweetList();
			list.addAll(tweets);
			context.write(hastag, list);
		}
	}

}
