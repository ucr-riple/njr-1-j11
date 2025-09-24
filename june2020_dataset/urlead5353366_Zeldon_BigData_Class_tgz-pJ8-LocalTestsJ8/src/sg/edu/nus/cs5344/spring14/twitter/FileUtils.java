package sg.edu.nus.cs5344.spring14.twitter;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileUtils {

	private FileUtils() {
		// Do not instantiate
	}

	public static Path[] getAllParts(Path folder, FileSystem fs) throws IOException {
		FileStatus[] listFiles = fs.globStatus(new Path(folder, "part-*"));
		if (listFiles.length == 0) {
			throw new FileNotFoundException("No part files in folder");
		}
		Path[] paths = new Path[listFiles.length];
		for (int i = 0; i < paths.length; i++) {
			paths[i] = listFiles[i].getPath();
		}
		return paths;
	}

}
