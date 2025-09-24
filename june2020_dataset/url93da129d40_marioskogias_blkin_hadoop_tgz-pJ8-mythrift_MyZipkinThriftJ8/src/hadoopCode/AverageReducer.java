package hadoopCode;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class AverageReducer extends
		Reducer<Text, LongWritable, Text, LongWritable> {

	private LongWritable average = new LongWritable();
	
	public void reduce(Text key, Iterable<LongWritable> values, Context context)
			throws IOException, InterruptedException {
		int count = 0;
		long amount = 0;
		for (LongWritable t : values) {
			amount += t.get();
			count++;
		}
		System.out.println(amount);
		System.out.println(count);
		average.set(amount/count);
		context.write(key, average);
	}
}