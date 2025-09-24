package startrekj.hpbasic;

import java.util.HashMap;
import java.util.Map;

import startrekj.hpbasic.statements.ExitException;
import startrekj.hpbasic.statements.IMAGE;

public class HPBasicProgram {

	public static Integer currentLineNumber;
	public static Integer nextLineNumber;
	
	public static boolean isTraceOn = false;

	public static Map<String, Integer> maxValueForLoopVariable = new HashMap<String, Integer>();

	public static Map<String, Integer> lineNumberForLoopVariable = new HashMap<String, Integer>();
	
	public static ProgramLines lines = new ProgramLines();

	protected void addLines(Line...lines) {
		this.lines.add(lines);
	}
	
	protected void run() {
		currentLineNumber = lines.getFirstLineNumber();

		while(currentLineNumber != null) {
			nextLineNumber = lines.getNextLineNumberAfter(currentLineNumber);

			Statement statement = lines.getStatementAtLineNumber(currentLineNumber);
			trace("line: " + currentLineNumber + " statement: " + statement);
			if(statement == null) {
				System.out.println("no statement at line " + currentLineNumber);
				System.out.println("exiting");
				return;
			}
			try {
				statement.execute();
			} catch(ExitException e) {
				System.out.println("program exited");
				return;
			} catch(Throwable e) {
				System.err.println("error in line: " + currentLineNumber);
				System.err.println(statement);
				e.printStackTrace();
				return;
			}
			
			currentLineNumber = nextLineNumber;
		}
		
	}
	private static void trace(String text) {
		if(isTraceOn)
			System.out.println("                  (trace) " + text);
	}

	@Override
	public String toString() {
		return lines.toString();
	}

	public static String getFormatString(int lineNumber) {
		Statement statement = HPBasicProgram.lines.getStatementAtLineNumber(lineNumber);
		if (statement instanceof IMAGE ) {
			IMAGE image = (IMAGE) statement;
			return image.getFormatString();
		}
		throw new RuntimeException("statement at line " + lineNumber + " is not an image");
	}
}
