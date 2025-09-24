package sg.edu.nus.cs5344.spring14.twitter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;


public class ConfUtils {
	public static Path getPath(Configuration conf, String attribute) {
		String file = conf.get(attribute);
		if (file == null) {
			throw new IllegalArgumentException(attribute + " was not set in the configuration");
		}
		return new Path(file);
	}
}
