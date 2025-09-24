package cz.mff.dpp.args.examples;

import cz.mff.dpp.args.Argument;
import cz.mff.dpp.args.Option;
import cz.mff.dpp.args.Parser;


/**
 * 
 * How not to define {@code @Option} setter.
 *
 */
public class SetterTest {
	
	public static class Options {
		@Option(name="-a")
		boolean test() {
			System.out.println("Inside test");
			
			return true;
		}
		
		@Option(name="--bee")
		String bee;
		
		
		@Argument(name="all")
		String[] arguments;
		
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Methods you can annotate with @Option are well defined. Here is used wrog signature ... \n\n");
		System.out.println("IllegalArgumentException expected ...\n");
		Options options = new Options();
		
		Parser parser = new Parser(options);
		
		String[] localArgs = { "-a"};
		

		
		parser.parse(localArgs);
	}
		
}
