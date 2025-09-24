package compiler;

public class Program {

	public static String successOrFailure(boolean b) {
		if (b)
			return "Success";
		return "Failure";
	}

	public static void main(String[] args) {

		// Command line arguments
		for (String arg : args) {
			System.out.println(arg);
		}

		
		String code = "declare a transreal declare b transreal set b 1 set a + b nullity"; 

		EsperCompiler compiler = new EsperCompiler();
		compiler.readCommandLineArguments(args);
		compiler.compile(code);
		
		System.out.println("Compiled!");
		System.out.println("Lexer status: "
				+ successOrFailure(compiler.lexerSuccess) + " | Errors: "
				+ compiler.lexerErrors);
		System.out.println("Parser status: "
				+ successOrFailure(compiler.parserSuccess) + " | Errors: "
				+ compiler.parserErrors);
	}

}
