package compiler;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

import compiler.EsperLexer;
import compiler.EsperParser;

public class EsperCompiler {

	public boolean lexerSuccess = false;
	public int lexerErrors = 0;
	public boolean parserSuccess = false;
	public int parserErrors = 0;
	
	//Output from the stages of the compilation process
	private EsperLexer lexer;
	private ParseTree parseRoot;
	private ArrayList<VariableInformation> variableList;
	
	//Flags
	private boolean flagLexerOutput = false;
	private boolean flagParserOutput = false;
	private boolean flagGeneratedCOutput = false;
	
	public void readCommandLineArguments(String[] args) {

		//Debug flags
		if (args.length == 0) 
			args = getDebugArgs();
		
		for (String arg : args)
			if (arg.toCharArray()[0] == '-')
				readFlag(arg.substring(1));
	}
	
	private String[] getDebugArgs() {
		ArrayList<String> argList = new ArrayList<String>();
		argList.add("-lex");
		argList.add("-par");
		argList.add("-opt");
		argList.add("-genc");
		return argList.toArray(new String[argList.size()]);
	}
	
	/**
	 * Reads a command line flag
	 */
	private void readFlag(String flag) {
		
		switch (flag) {
		case "lex":
			flagLexerOutput = true;
			break;
		case "par":
			flagParserOutput = true;
			break;
		case "genc":
			flagGeneratedCOutput = true;
			break;
			
		default:
			System.out.println("Error -" + flag + " is an unrecognised flag");
			break;
		}
	}
	
	/**
	 * Performs the parsing stage of the compiler
	 * @param tokens The tokens from the lexical analysis
	 */
	private void parseProcess(CommonTokenStream tokens) {
		//ANTLR parse
		EsperParser parser = new EsperParser(tokens);		
		EsperParser.program_return ret;

		try {
			ret = parser.program();
		} catch (RecognitionException e) {
			System.out.println("Exception occurred in parser!");
			e.printStackTrace();
			return;
		}
		parserSuccess = (parserErrors = parser.getNumberOfSyntaxErrors()) <= 0;
		
		//Acquire parse result
		CommonTree ast = (CommonTree) ret.getTree();
		
		//Post parse
		EsperPostParser postParser = new EsperPostParser();
		parseRoot = postParser.getParseTree(ast);
		postParser.getVariableList();
		variableList = postParser.variableList;
		
		//Optimise
		EsperOptimiser optimiser = new EsperOptimiser();
		parseRoot = optimiser.optimise(parseRoot);
		
		//Print output
		if (flagParserOutput) {
			System.out.println("Parser output:");
			parseRoot.print(0);
		}
	}
	
	/**
	 * Performs the lexical analysis stage of the compiler
	 * @param sourceCode The code of the program
	 */
	private void lexicalAnalysis(String sourceCode) {
		lexer = new EsperLexer(new ANTLRStringStream(sourceCode));
		lexerSuccess = (lexerErrors = lexer.getNumberOfSyntaxErrors()) <= 0;
		
		//Print output
		if (flagLexerOutput) {
			System.out.println("Lexer output: ");
			Token token;
			EsperLexer tokensOut = new EsperLexer(new ANTLRStringStream(sourceCode));
			while ((token = tokensOut.nextToken()).getType() != -1) {
				// Ignore whitespace
				if (token.getType() != EsperLexer.WHITESPACE)
					System.out.println("Token: " + token.getText() + " | "
							+ getTokenName(token.getType()));
			}
		}
	}
	
	private void generateCode(ParseTree root) {
		String code = EsperCGenerator.generate(root, variableList);
		
		if (flagGeneratedCOutput)
			System.out.println(code);
	}
	

	// http://www.antlr.org/wiki/pages/viewpage.action?pageId=789
	public void compile(String sourceCode) {

		//Strip whitespace, tabs - both are irrelevant
		sourceCode = sourceCode.replace("\n", "").replace("\r", "").replace("\t", "");
		
		//Strip comments (C style)
		//Regex from http://ostermiller.org/findcomment.html
		sourceCode = sourceCode.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)", "");

		//Lexical analysis
		lexicalAnalysis(sourceCode);

		//Parser
		parseProcess(new CommonTokenStream(lexer));
		
		//Code Generation
		generateCode(parseRoot);
	}

	// Uses reflection to get the token names from their types
	public static String getTokenName(int tokenType) {
		// Get all the fields of the lexical analyser - will be the token type
		// variables
		Field[] fields = EsperLexer.class.getFields();

		// Iterate through the fields
		for (Field field : fields) {
			if (field.getType() == int.class) {
				try {
					// If the field matches the token type then that field is
					// the token
					if (field.getInt(null) == tokenType) {
						return field.getName();
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return "UNKNOWN TOKEN";
	}

}