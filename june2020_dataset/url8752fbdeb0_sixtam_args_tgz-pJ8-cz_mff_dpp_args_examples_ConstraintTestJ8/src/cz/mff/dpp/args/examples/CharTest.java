package cz.mff.dpp.args.examples;


import cz.mff.dpp.args.Constraint;
import cz.mff.dpp.args.Logger;
import cz.mff.dpp.args.Option;
import cz.mff.dpp.args.Parser;


/**
 * 
 * Tests char and Character, use of comparator.
 * 
 *
 */
public class CharTest {
	
	public static final class Handler {

		private Handler() {

		}

		public static int compare(char x, char y) {
			return Character.valueOf(x).compareTo(y);
		}
	}
	
	static class CharTestOptions {
		@Option (name="-c")
		public char c;
		
		@Option (name="--character")
		Character character;
		
		@Option (name="--charSetter", defaultValues = {"b", "c"})
		public void charSetter(char c) {
			charFromSetter = c;
		}
		
		public char charFromSetter;
		
		@Option (name="--characterSetter")
		public void charSetter(Character c) {
			characterFromSetter = c;
		}
		public Character characterFromSetter;
		
		
		@Option (name="--char-constraint")
		@Constraint(min="a", max="c", comparator=Handler.class)
		char ch;
		

		
		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CharTestOptions charOptions = new CharTestOptions();
		Parser parser = new Parser(charOptions);
		
		if (args.length == 0) {
			String[] localArgs = {"-c", "e", "--character", "C", "--charSetter", "--characterSetter", "Q", "--char-constraint=b" };
			parser.parse(localArgs);
		} else {
			parser.parse(args);
		}
		
		System.out.println("char: " + charOptions.c);
		System.out.println("Character: " + charOptions.character);
		System.out.println("char from setter: " + charOptions.charFromSetter);
		System.out.println("Character from setter: " + charOptions.characterFromSetter);
		
		if (args.length == 0) {
			assert (charOptions.c == 'e');
			assert (charOptions.character.charValue() == 'C');
			assert (charOptions.charFromSetter == 'b');
			assert (charOptions.characterFromSetter.charValue() == 'Q');
		}
		
		
		

	}

}
