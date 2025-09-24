package sg.edu.nus.cs5344.spring14.twitter.hashCounts;

//Input file is hardcoded at "/TwitterInFiles/time_twitter.tar.gz" found in main
//Outputs are hardcoded at "/twitterMHOuts/" found in main
//Abnormal rows will be highlighted with "No._of_Abnormal_Rows:"
//This takes the inputfile, and parses the year-month at the 14th column, the hashtags at the 3rd column
// and outputs the (year-month_#hashtag, 1) value pair at the 1st Mapper
// The first reducer sums the frequency count and outputs (year-month_#hashtag, frequency)
// 2nd Mapper parse lines and output as (-1*freq, month_hashtag) for sorting
// 2nd Reducer take in sorted input from Mapper as (-1*freq, month_hashtag) and output as (month_hashtag)

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


public class MonthHashCount1 {

	//parse lines and output (key,value)pair as (month_hashtag, 1)
	public static class Mapper1 extends Mapper<LongWritable, Text, Text, IntWritable> {


		private final static IntWritable freq = new IntWritable(1);
		private Text month_hash = new Text();
		//int row = 0;

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			
			//row++;
			String line[]=value.toString().split("\\t");
			
			if (line.length==14){
				
				StringTokenizer tokenizer = new StringTokenizer(line[2]);
				
				while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();

					if (token.startsWith("#")){
						
						String[]tokens = token.split("#");
											
						for (int i=1; i < tokens.length; i++ ){
							month_hash.set(line[13].substring(0,7)+"_#"+tokens[i].replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().trim());
							context.write(month_hash, freq);
						}
					}
					
				}

				
			}else{
				month_hash.set("No._of_Abnormal_Rows:");
				context.write(month_hash, freq);
			}
			
						
			
		}
	}

	//sum up frequency values of unique "month_hashtag" and output (month_hashtag, frequency)
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
	
	//parse lines and output as (-1*freq, month_hashtag) for sorting
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
	
	//take in sorted input from Mapper as (-1*freq, month_hashtag) and output as (month_hashtag)
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
		job1.setJarByClass(MonthHashCount1.class);
		job1.setMapperClass(Mapper1.class);
		//job1.setCombinerClass(Combine1.class);
		job1.setReducerClass(Reducer1.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(IntWritable.class);
		job1.setNumReduceTasks(1);
	    
	    FileInputFormat.addInputPath(job1, new Path("/TwitterInFiles/time_twitter.tar.gz"));
		//FileInputFormat.addInputPath(job1, new Path("/TwitterInFiles/twitter_10k.tar.gz"));
	    FileOutputFormat.setOutputPath(job1, new Path("/twitterMHOuts/output1"));
	    job1.waitForCompletion(true);

		// ===== Stage 2 =====
		Job job2 = new Job(conf, "Stage 2: Sort");
		job2.setJarByClass(MonthHashCount1.class);
		job2.setMapperClass(Mapper2.class);
		// job1.setCombinerClass(IntSumReducer.class);
		job2.setReducerClass(Reducer2.class);
		job2.setOutputKeyClass(IntWritable.class);
		job2.setOutputValueClass(Text.class);
		job2.setNumReduceTasks(1);
		FileInputFormat.addInputPath(job2, new Path("/twitterMHOuts/output1"));
		FileOutputFormat.setOutputPath(job2, new Path("/twitterMHOuts/output2"));
		job2.waitForCompletion(true);

		System.exit(job2.waitForCompletion(true) ? 0 : 1);
	}

}