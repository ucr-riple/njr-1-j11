package sg.edu.nus.cs5344.spring14.twitter.Jobs;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.Tweet;

public class JobAParseText {

	private static final NullWritable NULL = NullWritable.get();

	public static class MapperImpl extends Mapper<LongWritable, Text, Tweet, NullWritable> {
		private String lineBuffer = null;

		private final int k = 5;
		private Deque<String> lastKLines = new ArrayDeque<String>();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = getLine(value);
			lastKLines.add(line);
			while (lastKLines.size() > k) {
				lastKLines.poll();
			}

			if (line == null || line.isEmpty()) {
				return;
			}

			// Handle line breaks escaped by backslash
			if (lineBuffer != null) {
				// This line is a continuation of the previous
				line = lineBuffer + line;
				lineBuffer = null;
			}
			if (line.charAt(line.length() - 1) == '\\') {
				// This line continues on the next
				lineBuffer = line.substring(0, line.length() - 1);
				return;
			}

			// Detect abnormal lines
			String[] lineSplit = Tweet.split(line);
			if (lineSplit.length > Tweet.EXPECTED_COLUMNS) {
				printError(line, "Too many columns: " + lineSplit.length);
				line = value.toString();
			} else if (lineSplit.length < Tweet.EXPECTED_COLUMNS) {
				lineBuffer = line;
				return;
			}

			// Create tweet
			try {
				context.write(new Tweet(lineSplit), NULL);
			} catch (IllegalArgumentException e) {
				printError(line, e.getMessage());
			}
		}

		private String getLine(Text value) {
			String string = value.toString();
			// Remove escaped tab characters
			return string.replaceAll("\\\\\\t", "    ");
		}

		private void printError(String line, String reason) {
			System.out.println("========================");
			System.out.println("Could not parse tweet: " + line);
			System.out.println("Reason: " + reason);
			System.out.println("Buffer:");
			System.out.println(lineBuffer);
			System.out.println("Last " + k + " Lines:");
			for (String s : lastKLines) {
				System.out.println(s);
			}
		}
	}

	public static class ReducerImpl extends Reducer<Tweet, NullWritable, Tweet, NullWritable> {
		@Override
		protected void reduce(Tweet tweet, Iterable<NullWritable> nothing, Context context) throws IOException,
				InterruptedException {
			context.write(tweet, NULL);
		}
	}

}
