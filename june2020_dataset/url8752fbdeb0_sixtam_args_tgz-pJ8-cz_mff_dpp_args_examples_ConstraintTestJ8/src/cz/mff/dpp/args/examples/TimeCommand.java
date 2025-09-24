package cz.mff.dpp.args.examples;

import java.util.Arrays;

import cz.mff.dpp.args.*;

/**
 * 
 * Time command demo ... not to forget to use "--".
 * 
 *
 */
public class TimeCommand {

	@SuppressWarnings("unused")
	private static final class TimeOptions {

		private static final String FORMAT_DESC = "Specify output format, possibly "
				+ "overriding the format specified in the environment variable TIME.";

		private static final String PORT_DESC = "Use the portable output format.";

		private static final String OUT_DESC = "Do not send the results to stderr, "
				+ "but overwrite the specified file.";

		private static final String APPEND_DESC = "(Used together with -o.) "
				+ "Do not overwrite but append.";

		private static final String VERBOSE_DESC = "Give very verbose output about "
				+ "all the program knows about.";

		private static final String HELP_DESC = "Print a usage message on standard "
				+ "output and exit successfully.";

		private static final String VERSION_DESC = " Print version information "
				+ "on standard output, then exit successfully.";

		// Options
		@Option(name = "-V", aliases = { "--version" })
		public boolean printVersion;

		@Option(name = "--help", description = HELP_DESC)
		public boolean printUsage;

		@Option(name = "-v", aliases = { "--verbose" }, description = VERBOSE_DESC)
		void setVerbose() {
			verbose = true;
		}

		public boolean verbose;

		@Option(name = "-a", mustUseWith = { "-o" }, description = APPEND_DESC)
		public boolean append;

		@Option(name = "-p", aliases = { "--portability" }, description = PORT_DESC)
		public boolean portability;

		@Option(name = "-f", aliases = { "--format" }, description = FORMAT_DESC)
		public String format;

		@Option(name = "-o", aliases = { "--output" }, description = OUT_DESC)
		private void setOutputFile(Integer[] outputFile) {
			// could do more work here ...
			System.out.println(Arrays.toString(outputFile));
			//this.outputFile = outputFile[0];
		}

		public String outputFile;

		@Argument(index = 0, name = "command", description = "command")
		String command;

		@Argument(index = 0, name = "all", description = "all arguments")
		public String[] arguments;

	}

	public static void main(String[] args) {
		TimeOptions timeOptions = new TimeOptions();
		Parser parser = new Parser(timeOptions);

		try {

			parser.parse(args);
			
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			parser.usage();
		}

		// program logic

	}
}
