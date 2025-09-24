package cz.mff.dpp.args.examples;


import cz.mff.dpp.args.*;

/**
 * 
 * Tests correct handling of option list terminator ("--")
 * 
 *
 */
public class OptionListTerminator {
	
	
	public static class Options {
		@Option(name="-a")
		int a;
		
		@Option(name="--bee")
		String bee;
		
		
		@Argument(name="all")
		String[] arguments;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Options options = new Options();
		
		Parser parser = new Parser(options);
		
		String[] localArgs = { "-a", "5", "--bee", "SuNDaY", "--", "--day", "-b=", "-b", "--option=safs"};
		
		int separatorIndex = -1;
		
		for (int i = 0; i < localArgs.length; ++i) {
			if (localArgs[i].equals("--")) {
				separatorIndex = i;
				break;
			}
		}
		
		assert separatorIndex != -1 : "No option list terminator!";
		

		
		parser.parse(localArgs);
		
	
		boolean result = true;
		
		for (int i = separatorIndex + 1; i < localArgs.length; ++i) {
			
			
			
			if ( localArgs[i].equals(options.arguments[i - separatorIndex - 1 ])) {
				System.out.printf("'%s' == '%s'\n", localArgs[i], options.arguments[i - separatorIndex - 1 ]);
				
				
			} else {
				System.out.printf("'%s' != '%s'\n", localArgs[i], options.arguments[i - separatorIndex - 1 ]);
				result = false;
			}
			
		}
		
		assert result: "Test failed";
		
		if (result) {
			System.out.println("PASSED");
		} else {
			System.out.println("FAILED");
		}
		

		

	}

}
