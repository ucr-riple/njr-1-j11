package cz.mff.dpp.args.examples;

import java.util.Arrays;
import java.util.List;

import cz.mff.dpp.args.*;


/**
 * 
 * Multi-values demo.
 *
 */
public class MultiValuedExample {

	@SuppressWarnings("unused")
	private static final class Options {
		@Option(name = "--list-of-doubles")
		List<Double> doubles;


		@Option(name = "--array-setter")
		void stringArraySetter(String[] values) {
			System.out.println("--array-setter: " + Arrays.toString(values));
		}
		
		@Argument(name="all")
		List<String> freeArguments;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws ParseException {
		

		if (args.length != 0) {
			Options options = new Options();
			Parser parser = new Parser(options);
			parser.parse(args);
			return;
		}

		{
			Options options = new Options();
			Parser parser = new Parser(options);
			String localArgs[] = {"--list-of-doubles=1.2", "-2.3", "15", "-0.1"};
			System.out.println("Input: " + Arrays.toString(localArgs));
			parser.parse(localArgs);
			System.out.println("List of doubles: " + options.doubles);

		}

		{
			Options options = new Options();
			Parser parser = new Parser(options);
			String localArgs[] = { "--array-setter", "a", "abc", "QWE", "weWe" };
			System.out.println("\nInput: " + Arrays.toString(localArgs));
			parser.parse(localArgs);

		}

		{
			System.out.println("\nNow together: ");
			Options options = new Options();
			Parser parser = new Parser(options);
			String localArgs[] = { "--array-setter", "a", "abc", "QWE", "weWe",
					"--list-of-doubles=1.2", "-2.3", "15", "-0.1" };
			System.out.println("Input: " + Arrays.toString(localArgs));
			parser.parse(localArgs);
			System.out.println("List of doubles: " + options.doubles);

		}
		
		System.out.println("\nWorks for arguments as well: just be aware what is a free argument\n");
		
		{

			Options options = new Options();
			Parser parser = new Parser(options);
			String localArgs[] = { "arg1", "arg2", "--array-setter", "a", "abc", "QWE", "weWe",
					"--list-of-doubles=1.2", "-2.3", "15", "-0.1", "--", "arg3", "arg4" };
			System.out.println("Input: " + Arrays.toString(localArgs));
			parser.parse(localArgs);
			System.out.println("List of doubles: " + options.doubles);
			System.out.println("Free arguments: " + options.freeArguments);

		}

	}

}
