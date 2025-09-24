package sg.edu.nus.cs5344.spring14.twitter.hashCounts;

//input files are hard-coded at "/TwitterInFiles/twitter_10k.tar.gz"
//output file will go to "/twitterOuts/output1"
//View by Chin Leong

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class HashCount1 {

	public static class Mapper1 extends Mapper<LongWritable, Text, Text, IntWritable> {


		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			
			String line = value.toString();

			StringTokenizer tokenizer = new StringTokenizer(line);
			
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();

				if (token.startsWith("#")){
					
					String[]tokens = token.split("#");
										
					for (int i=1; i < tokens.length; i++ ){
						word.set("#"+tokens[i].replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().trim());
					
						context.write(word, one);
					}
				}
				
			}
		}
	}

	public static class Reducer1 extends Reducer<Text, IntWritable, Text, IntWritable> {
		
		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
		
			int sum = 0;

			for (IntWritable val : values) {
				
				sum += val.get();
				
			}
			
			context.write(key, new IntWritable(sum));
			
			
		}
	}
	
	public static class Mapper2 extends Mapper<LongWritable, Text, IntWritable, Text>{
		
		private Text word = new Text();
		private IntWritable freq = new IntWritable(0);
		
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException{
			
			String line [] = value.toString().split("[\\s]");
			
			word.set(line[0].trim());
			freq.set(-1*Integer.parseInt(line[1].trim()));
			context.write(freq, word);
						
		}
		
	}
	
	public static class Reducer2 extends Reducer<IntWritable, Text, Text, IntWritable> {
		
		private IntWritable Freq = new IntWritable(0);
		
		@Override
		protected void reduce(IntWritable key, Iterable<Text> value, Context context)
				throws IOException, InterruptedException {
			
			for(Text val:value){
				Freq.set(-1*(key.get()));
				context.write(val, Freq);
			}
		}
	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		//conf.addResource(new Path("/home/hadoop/hadoop/hadoop-1.2.1/conf/core-site.xml"));
		//conf.addResource(new Path("/home/hadoop/hadoop/hadoop-1.2.1/conf/hdfs-site.xml"));

		// ===== Stage 1 =====
		Job job1 = new Job(conf, "Stage 1: Frequency Count");
		job1.setJarByClass(HashCount1.class);
		job1.setMapperClass(Mapper1.class);
		//job1.setCombinerClass(Combine1.class);
		job1.setReducerClass(Reducer1.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(IntWritable.class);
		job1.setNumReduceTasks(1);

	    FileInputFormat.addInputPath(job1, new Path("/TwitterInFiles/twitter_10k.tar.gz"));
	    FileOutputFormat.setOutputPath(job1, new Path("/twitterOuts/output1"));
	    job1.waitForCompletion(true);

		// ===== Stage 2 =====
		Job job2 = new Job(conf, "Stage 2: Sort");
		job2.setJarByClass(HashCount1.class);
		job2.setMapperClass(Mapper2.class);
		// job1.setCombinerClass(IntSumReducer.class);
		job2.setReducerClass(Reducer2.class);
		job2.setOutputKeyClass(IntWritable.class);
		job2.setOutputValueClass(Text.class);
		job2.setNumReduceTasks(1);
		FileInputFormat.addInputPath(job2, new Path("/twitterOuts/output1"));
		FileOutputFormat.setOutputPath(job2, new Path("/twitterOuts/output2"));
		job2.waitForCompletion(true);

		System.exit(job2.waitForCompletion(true) ? 0 : 1);
	}

}