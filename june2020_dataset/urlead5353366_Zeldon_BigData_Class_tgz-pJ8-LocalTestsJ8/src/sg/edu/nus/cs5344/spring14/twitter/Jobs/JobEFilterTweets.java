package sg.edu.nus.cs5344.spring14.twitter.Jobs;

import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.DAY_STATS_DATA_FOLDER_ATT;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.FILTER_FIRST_DAY;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.FILTER_MIN_HASHTAGS_PER_DAY;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import sg.edu.nus.cs5344.spring14.twitter.ConfUtils;
import sg.edu.nus.cs5344.spring14.twitter.FileUtils;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Day;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Tweet;

public class JobEFilterTweets {

	public static Set<Day> loadIncludedDays(Configuration conf) throws IOException {
		Set<Day> includedDays = new HashSet<Day>();
		FileSystem fs = FileSystem.get(conf);
		Path dataStatsFolder = ConfUtils.getPath(conf, DAY_STATS_DATA_FOLDER_ATT);

		Day day = new Day();
		VIntWritable count = new VIntWritable();
		for (Path path : FileUtils.getAllParts(dataStatsFolder, fs)) {
			Reader reader = null;
			try {
				reader = new SequenceFile.Reader(fs, path, conf);
				while (reader.next(day, count)) {
					if (count.get() >= FILTER_MIN_HASHTAGS_PER_DAY && day.get() >= FILTER_FIRST_DAY) {
						includedDays.add(day.copy());
					}
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		}
		return includedDays;
	}

	public static class MapperImpl extends Mapper<Tweet, NullWritable, Tweet, NullWritable> {
		private Set<Day> includedDays;

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			Configuration conf = context.getConfiguration();
			includedDays = loadIncludedDays(conf);
		}

		@Override
		protected void map(Tweet tweet, NullWritable nothing, Context context) throws IOException, InterruptedException {
			if (includedDays.contains(tweet.getTime().getDay())) {
				context.write(tweet, nothing);
			}
		}
	}

	public static class ReducerImpl extends Reducer<Tweet, NullWritable, Tweet, NullWritable> {
		private static final NullWritable NULL = NullWritable.get();

		@Override
		protected void reduce(Tweet key, Iterable<NullWritable> counts, Context context) throws IOException,
				InterruptedException {
			context.write(key, NULL);
		}
	}
}
