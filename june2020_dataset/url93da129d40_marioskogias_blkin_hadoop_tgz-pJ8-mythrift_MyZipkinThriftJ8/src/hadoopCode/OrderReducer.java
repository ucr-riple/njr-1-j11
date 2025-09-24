package hadoopCode;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class OrderReducer extends
		Reducer<LongWritable, LongWritable, LongWritable, NullWritable> {

	private LongWritable diff = new LongWritable();
	private NullWritable myNull = NullWritable.get();
	
	public void reduce(LongWritable key, Iterable<LongWritable> values, Context context)
			throws IOException, InterruptedException {
		
		for (LongWritable t : values) {
			diff.set(t.get());
			context.write(diff, myNull);
		}
	}
}