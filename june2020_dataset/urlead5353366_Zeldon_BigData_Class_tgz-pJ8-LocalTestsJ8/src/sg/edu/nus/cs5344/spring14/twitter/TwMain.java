package sg.edu.nus.cs5344.spring14.twitter;

import static sg.edu.nus.cs5344.spring14.twitter.FileLocations.getOutputForJob;
import static sg.edu.nus.cs5344.spring14.twitter.FileLocations.getSpecaialFolder;
import static sg.edu.nus.cs5344.spring14.twitter.KeyValueFormatters.getTabDelimimted;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.DAY_STATS_DATA_FOLDER_ATT;
import static sg.edu.nus.cs5344.spring14.twitter.TwConsts.TRENDS_DATA_FOLDER_ATT;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import sg.edu.nus.cs5344.spring14.twitter.Jobs.JobAParseText;
import sg.edu.nus.cs5344.spring14.twitter.Jobs.JobBCountHashtags;
import sg.edu.nus.cs5344.spring14.twitter.Jobs.JobCFindTrends;
import sg.edu.nus.cs5344.spring14.twitter.Jobs.JobDDayStats;
import sg.edu.nus.cs5344.spring14.twitter.Jobs.JobEFilterTweets;
import sg.edu.nus.cs5344.spring14.twitter.Jobs.JobFTweetsForTrends;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Day;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Hashtag;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Trend;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Tweet;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.DayCountPair;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.DayHashtagPair;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.TweetList;

public class TwMain {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// Parse command line
		final Configuration conf = new Configuration();
		final String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		CmdArguments cmdArgs = CmdArguments.instantiate(otherArgs);

		prepOutDir(conf, getSpecaialFolder());
		conf.set(DAY_STATS_DATA_FOLDER_ATT, getOutputForJob("D").toString());
		conf.set(TRENDS_DATA_FOLDER_ATT, getOutputForJob("C").toString());

		List<Job> jobs = new ArrayList<Job>();

		// The order is not alphabetical!

		if (!cmdArgs.skipJob("A")) {
			throw new RuntimeException("Job A is Hardcoded not to run. Please use -skip A or change source code");
			// jobs.add(createJobA(conf, getInput(), getOutputForJob("A")));
		}
		if (!cmdArgs.skipJob("D")) {
			jobs.add(createJobD(conf, getOutputForJob("A"), getOutputForJob("D")));
		}
		if (!cmdArgs.skipJob("E")) {
			jobs.add(createJobE(conf, getOutputForJob("A"), getOutputForJob("E")));
		}

		if (!cmdArgs.skipJob("B")) {
			jobs.add(createJobB(conf, getOutputForJob("E"), getOutputForJob("B")));
		}
		if (!cmdArgs.skipJob("C")) {
			jobs.add(createJobC(conf, getOutputForJob("B"), getOutputForJob("C")));
		}
		if (!cmdArgs.skipJob("F")) {
			jobs.add(createJobF(conf, getOutputForJob("E"), getOutputForJob("F")));
		}

		for (Job job : jobs) {
			System.out.println("\nRunning Job:" + job.getJobName());
			if (!job.waitForCompletion(true)) {
				throw new RuntimeException("Job failed: " + job.getJobName());
			}
		}

		printTrends(conf);
		printTweetsForTrends(conf);
	}

	private static Job createJobA(final Configuration conf, final Path input, final Path output) throws IOException {
		prepOutDir(conf, output);

		Job job = new Job(conf, "Parse Tweets");
		job.setJarByClass(JobAParseText.class);
		// Map
		FileInputFormat.addInputPath(job, input);
		// job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setMapperClass(JobAParseText.MapperImpl.class);
		job.setMapOutputKeyClass(Tweet.class);
		job.setMapOutputValueClass(NullWritable.class);
		// Reduce
		job.setReducerClass(JobAParseText.ReducerImpl.class);
		job.setOutputKeyClass(Tweet.class);
		job.setOutputValueClass(NullWritable.class);
		// Store intermediate data in as sequence file (binary) format
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		FileOutputFormat.setOutputPath(job, output);
		return job;
	}

	private static Job createJobB(final Configuration conf, final Path input, final Path output) throws IOException {
		prepOutDir(conf, output);

		Job job = new Job(conf, "Count Hashtags");
		job.setJarByClass(JobBCountHashtags.class);
		// Map
		FileInputFormat.addInputPath(job, input);
		// job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setMapperClass(JobBCountHashtags.MapperImpl.class);
		job.setMapOutputKeyClass(DayHashtagPair.class);
		job.setMapOutputValueClass(VIntWritable.class);
		// Reduce
		job.setReducerClass(JobBCountHashtags.ReducerImpl.class);
		job.setOutputKeyClass(DayHashtagPair.class);
		job.setOutputValueClass(VIntWritable.class);
		// Store intermediate data in as sequence file (binary) format
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		FileOutputFormat.setOutputPath(job, output);
		return job;
	}

	private static Job createJobC(final Configuration conf, final Path input, final Path output) throws IOException {
		prepOutDir(conf, output);

		Job job = new Job(conf, "Find Trends");
		job.setJarByClass(JobCFindTrends.MapperImpl.class);
		// Map
		FileInputFormat.addInputPath(job, input);
		// job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setMapperClass(JobCFindTrends.MapperImpl.class);
		job.setMapOutputKeyClass(Hashtag.class);
		job.setMapOutputValueClass(DayCountPair.class);
		// Reduce
		job.setReducerClass(JobCFindTrends.ReducerImpl.class);
		job.setOutputKeyClass(Hashtag.class);
		job.setOutputValueClass(Trend.class);
		// Store intermediate data in as sequence file (binary) format
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		FileOutputFormat.setOutputPath(job, output);
		return job;
	}

	private static Job createJobD(final Configuration conf, final Path input, final Path output) throws IOException {
		prepOutDir(conf, output);

		Job job = new Job(conf, "Day Stats");
		job.setJarByClass(JobDDayStats.class);
		// Map
		FileInputFormat.addInputPath(job, input);
		// job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setMapperClass(JobDDayStats.MapperImpl.class);
		job.setMapOutputKeyClass(Day.class);
		job.setMapOutputValueClass(VIntWritable.class);
		// Reduce
		job.setReducerClass(JobDDayStats.ReducerImpl.class);
		job.setOutputKeyClass(Day.class);
		job.setOutputValueClass(VIntWritable.class);
		// Store intermediate data in as sequence file (binary) format
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		FileOutputFormat.setOutputPath(job, output);
		return job;
	}

	private static Job createJobE(final Configuration conf, final Path input, final Path output) throws IOException {
		prepOutDir(conf, output);

		Job job = new Job(conf, "Filtering Tweets");
		job.setJarByClass(JobEFilterTweets.class);
		// Map
		FileInputFormat.addInputPath(job, input);
		// job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setMapperClass(JobEFilterTweets.MapperImpl.class);
		job.setMapOutputKeyClass(Tweet.class);
		job.setMapOutputValueClass(NullWritable.class);
		// Reduce
		job.setReducerClass(JobEFilterTweets.ReducerImpl.class);
		job.setOutputKeyClass(Tweet.class);
		job.setOutputValueClass(NullWritable.class);
		// Store intermediate data in as sequence file (binary) format
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		FileOutputFormat.setOutputPath(job, output);
		return job;
	}

	private static Job createJobF(final Configuration conf, final Path input, final Path output) throws IOException {
		prepOutDir(conf, output);

		Job job = new Job(conf, "Finding Tweets for Trends");
		job.setJarByClass(JobFTweetsForTrends.class);
		// Map
		FileInputFormat.addInputPath(job, input);
		// job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setMapperClass(JobFTweetsForTrends.MapperImpl.class);
		job.setMapOutputKeyClass(Hashtag.class);
		job.setMapOutputValueClass(Tweet.class);
		// Reduce
		job.setReducerClass(JobFTweetsForTrends.ReducerImpl.class);
		job.setOutputKeyClass(Hashtag.class);
		job.setOutputValueClass(TweetList.class);
		// Store intermediate data in as sequence file (binary) format
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		FileOutputFormat.setOutputPath(job, output);
		return job;
	}

	/**
	 * Prints the tweets for each trend.
	 */
	private static void printTweetsForTrends(Configuration conf) throws IOException {
		// Write human readable files
		FileSystem fs = FileSystem.get(conf);
		OutputStream fileStream = null;
		KeyValueFormatter<Hashtag, TweetList> formatter = new TweetFormater();
		try {
			fileStream = fs.create(FileLocations.getTextOutputPath("tweetsForTrends.txt"));
			printOutput(conf, getOutputForJob("F"), new Hashtag(), new TweetList(), formatter, new PrintStream(fileStream));
		} finally {
			if (fileStream != null) {
				fileStream.close();
			}
		}
	}

	private static void printTrends(Configuration conf) throws IOException {
		// Write human readable files
		FileSystem fs = FileSystem.get(conf);
		OutputStream fileStream = null;
		KeyValueFormatter<Hashtag, Trend> formatter = getTabDelimimted();
		try {
			fileStream = fs.create(FileLocations.getTextOutputPath("trends.txt"));
			printOutput(conf, getOutputForJob("C"), new Hashtag(), new Trend(), formatter, new PrintStream(fileStream), System.out);
		} finally {
			if (fileStream != null) {
				fileStream.close();
			}
		}
	}

	/**
	 * Prints small files, for which it would be a waste to run an entire job.
	 *
	 * @param conf
	 * @param folder
	 * @param key
	 * @param value
	 * @param outs
	 * @throws IOException
	 */
	private static <K extends Writable, V extends Writable> void printOutput(Configuration conf, Path folder, K key,
			V value, KeyValueFormatter<K, V> formatter, PrintStream... outs) throws IOException {
		FileSystem fs = FileSystem.get(conf);

		for (Path path : FileUtils.getAllParts(folder, fs)) {
			for (PrintStream out : outs) {
				out.println("// FROM FILE: " + path);
			}
			Reader reader = null;
			try {
				reader = new SequenceFile.Reader(fs, path, conf);
				while (reader.next(key, value)) {
					for (PrintStream out : outs) {
						out.println(formatter.format(key, value));
					}
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		}
	}

	private static void prepOutDir(final Configuration conf, final Path dir) throws IOException {
		final FileSystem fs = FileSystem.get(conf);
		fs.delete(dir, true);
		fs.mkdirs(dir.getParent());
	}

}
