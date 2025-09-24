package sg.edu.nus.cs5344.spring14.twitter;

import org.apache.hadoop.fs.Path;

/**
 * A utility/constant class that define the location of the file we are working on
 * @author tobber
 *
 */
public class FileLocations {

	public static final String INPUT_FOLDER = "/input/";
	public static final String TEMP_FOLDER = "/temp/";
	public static final String SPECIAL_FOLDER = TEMP_FOLDER + "SPECIAL/";
	public static final String OUTPUT_FOLDER = "/output/";


	public static Path getInput() {
		return new Path(root() + INPUT_FOLDER);
	}

	public static Path getOutputForJob(String jobId) {
		return new Path(root() + TEMP_FOLDER + jobId +"/");
	}

	public static Path getTextOutputPath(String name) {
		return new Path(root() + OUTPUT_FOLDER + name);
	}

	private static String root() {
		return CmdArguments.getArgs().getWorkingDir();
	}

	public static Path getSpecaialFolder() {
		return new Path(root() + SPECIAL_FOLDER);
	}

	public static Path getSpecialFile(String string) {
		return new Path(root() + SPECIAL_FOLDER + "FirstDay.txt");
	}
}
