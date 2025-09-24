package hadoopCode;

import java.io.IOException;
import java.util.Calendar;

import mythrift.Annotation;
import mythrift.Span;

import org.apache.commons.codec.binary.Base64;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TMemoryBuffer;

public class RecentMapper extends
		Mapper<LongWritable, Text, Text, LongWritable> {

	AnnotationChooser chooser = new Chooser();
	
	private Span getSpanFromString(String s) {
		TMemoryBuffer trans = new TMemoryBuffer(10);
		byte[] decoded = Base64.decodeBase64(s.getBytes());
		trans.write(decoded, 0, decoded.length);
		TBinaryProtocol tbp = new TBinaryProtocol(trans);
		Span span = new Span();
		try {
			span.read(tbp);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return span;
	}

	private LongWritable timestamp = new LongWritable();
	private Text id = new Text();

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		Span span = getSpanFromString(line);
		if (chooser.shouldKeepSpan(span)) {
			for (Annotation a : span.annotations) {
				if (chooser.shouldKeepAnnotation(a)) {
					id.set(chooser.getId(span, a));
					timestamp.set(a.timestamp);
					context.write(id, timestamp);
				}

			}
		}

	}

}
