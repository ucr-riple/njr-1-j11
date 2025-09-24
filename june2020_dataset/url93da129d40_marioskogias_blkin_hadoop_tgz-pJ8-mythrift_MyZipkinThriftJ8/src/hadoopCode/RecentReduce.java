package hadoopCode;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class RecentReduce extends
		Reducer<Text, LongWritable, Text, LongWritable> {

	private LongWritable diff = new LongWritable();
	private LongWritable timestamp = new LongWritable();
	
	public void reduce(Text key, Iterable<LongWritable> values, Context context)
			throws IOException, InterruptedException {
		
		Iterator<LongWritable> it = values.iterator();
		LongWritable v1 = new LongWritable();
		LongWritable v2 = new LongWritable();
		Text service = new Text();
		try {
			v1.set(it.next().get());
			v2.set(it.next().get());
		} catch (NoSuchElementException e) {
			return;
		}
		if (v1.get() > v2.get()) {
			diff.set(v1.get() - v2.get());
			timestamp.set(v2.get());
		}
		else {
			diff.set(v2.get() - v1.get());
			timestamp.set(v1.get());
		}
		service.set(key.toString().split(":")[2]);
		context.write(service, diff);
	}
}