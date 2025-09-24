package cz.mff.dpp.args.examples;

import cz.mff.dpp.args.Constraint;
import cz.mff.dpp.args.Option;
import cz.mff.dpp.args.ParseException;
import cz.mff.dpp.args.Parser;

/**
 * 
 * How to use Constraints.
 * 
 *
 */
public class ConstraintTest {
	
	
	// TODO if private -> cannot create instance in valueFromString()!!!
	public static final class TestClass {
		private String value;
		
		public TestClass(String value) {
			this.value = value;
			System.out.println(value);
		}
		
		@Override
		public String toString() {
			return value;
		}

	}

	@SuppressWarnings("unused")
	private static final class ConstraintTestOptions {

		@Option(name = "--day")
		@Constraint(allowedValues = { "monday", "friday" })
		String day;
		
		@Option(name = "--three-letters")
		@Constraint(regexp = "[a-zA-Z]{3}")
		String threeLetters;
		

		@Option(name = "-p", aliases = { "--port" })
		@Constraint(min = "1025", max = "65535")
		double port;
		
		
		@Option(name = "--list-of-ports")
		@Constraint(min = "1024", max = "65535")
		java.util.List<Integer> ports;
		
		@Option(name = "--array-of-days")
		@Constraint(allowedValues = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY",
			    "THURSDAY", "FRIDAY", "SATURDAY"  })
		String[] days;
		
		
		@Option(name = "--some-letters")
		@Constraint(allowedValues = { "A", "b", "C", "d"  }, ignoreCase = true)
		char[] someLetters;
		
		@Option(name="--test-class")
		TestClass testClass;

	}

	public static void main(String[] args) throws Exception {
		ConstraintTestOptions constraintOption = new ConstraintTestOptions();
		Parser parser = new Parser(constraintOption);

		String[] arguments = new String[] { "--day", "monday", "-p", "2000", "--three-letters", "abc", "--test-class", "test" };

		parser.parse(arguments);
		parser.usage();


		
		System.out.println("How not to pass checks: ... \n\n ");

		System.out.println("Exception expected ...");

		arguments = new String[] { "--day", "bla" };
		parser = new Parser(constraintOption);
		try {
			parser.parse(arguments);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		System.out.println("\n\nMIN: Another exception expected ...");
		arguments = new String[] { "--port", "1000" };
		parser = new Parser(constraintOption);
		try {
			parser.parse(arguments);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("\n\nMAX: Another exception expected ...");
		arguments = new String[] { "--port", "70000" };
		parser = new Parser(constraintOption);
		try {
			parser.parse(arguments);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("\n\nREGEXP: Another exception expected ...");
		arguments = new String[] { "--three-letters", "a1d" };
		parser = new Parser(constraintOption);
		try {
			parser.parse(arguments);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("\n\nLIST: Check values ... should be ok");
		arguments = new String[] { "--list-of-ports", "1025", "1026", "1027", "1028" };
		parser = new Parser(constraintOption);
		try {
			parser.parse(arguments);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("\n\nLIST: Exception expected ... ");
		arguments = new String[] { "--list-of-ports", "-10", "-1026", "-1027", "1028" };
		parser = new Parser(constraintOption);
		try {
			parser.parse(arguments);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("\n\nARRAY: Check values ... should be ok");
		arguments = new String[] { "--array-of-days", "MONDAY", "SUNDAY" };
		parser = new Parser(constraintOption);
		try {
			parser.parse(arguments);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("\n\nARRAY: Exception expected ... ");
		arguments = new String[] { "--array-of-days", "MONDAY", "SUN___DAY" };
		parser = new Parser(constraintOption);
		try {
			parser.parse(arguments);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("\n\nIGNORE CASE: ... should be ok");
		arguments = new String[] { "--some-letters", "a", "B", "c", "D" };
		parser = new Parser(constraintOption);
		try {
			parser.parse(arguments);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for (char letter: constraintOption.someLetters) {
			System.out.printf("%s", letter);
		}
		System.out.println();

	}
}
