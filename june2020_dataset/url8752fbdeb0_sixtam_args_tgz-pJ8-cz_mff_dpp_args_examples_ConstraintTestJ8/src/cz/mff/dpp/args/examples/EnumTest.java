package cz.mff.dpp.args.examples;

import cz.mff.dpp.args.Option;
import cz.mff.dpp.args.Parser;


/**
 * 
 * Simple Enum test/example.
 * 
 *
 */
public final class EnumTest {
	
	private static enum Day {
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
	    THURSDAY, FRIDAY, SATURDAY 
	}

	static class EnumTestOptions {		
		@Option (name="-d")
		public Day day;
		
		@Option (name="--day")
		public void daySetter(Day day) {
			dayFromSetter = day;
			
		}
		
		public Day dayFromSetter;
	}

	public static void main(String[] args) throws Exception {

		EnumTestOptions enumOptions = new EnumTestOptions();
		Parser parser = new Parser(enumOptions);
		
		Day arg1 = Day.SUNDAY;
		Day arg2 = Day.THURSDAY;

		if (args.length == 0) {
			String[] localArgs = { "-d", "SuNDaY", "--day", arg2.toString()};
			parser.parse(localArgs);
		
		} else {
			parser.parse(args);

		}

		System.out.printf("-d: %s\n", enumOptions.day);
		System.out.printf("--day: %s\n", enumOptions.dayFromSetter);
		
		if (args.length == 0) {
			assert enumOptions.day.equals(arg1) : "-d set to wrong value!";
			assert enumOptions.dayFromSetter.equals(arg2) : "--day set to wrong value!";
		}
		

	}

}
